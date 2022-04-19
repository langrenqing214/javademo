package com.thundersoft.model.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.OpenHashSet;

public final class CompositeDisposableWrapper implements Disposable, DisposableContainer {
    OpenHashSet<Disposable> resources;
    volatile boolean disposed;

    public CompositeDisposableWrapper() {
    }

    public CompositeDisposableWrapper(@NonNull Disposable... resources) {
        ObjectHelper.requireNonNull(resources, "resources is null");
        this.resources = new OpenHashSet(resources.length + 1);
        Disposable[] var2 = resources;
        int var3 = resources.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Disposable d = var2[var4];
            ObjectHelper.requireNonNull(d, "Disposable item is null");
            this.resources.add(d);
        }

    }

    public CompositeDisposableWrapper(@NonNull Iterable<? extends Disposable> resources) {
        ObjectHelper.requireNonNull(resources, "resources is null");
        this.resources = new OpenHashSet();
        Iterator var2 = resources.iterator();

        while(var2.hasNext()) {
            Disposable d = (Disposable)var2.next();
            ObjectHelper.requireNonNull(d, "Disposable item is null");
            this.resources.add(d);
        }

    }

    public OpenHashSet<Disposable> getResources() {
        return this.resources;
    }

    public void dispose() {
        if (!this.disposed) {
            OpenHashSet set;
            synchronized(this) {
                if (this.disposed) {
                    return;
                }

                this.disposed = true;
                set = this.resources;
                this.resources = null;
            }

            this.dispose(set);
        }
    }

    private void dispose(OpenHashSet<Disposable> set) {
        if (set != null) {
            List<Throwable> errors = null;
            Object[] array = set.keys();
            Object[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Object o = var4[var6];
                if (o instanceof Disposable) {
                    try {
                        ((Disposable)o).dispose();
                    } catch (Throwable var9) {
                        Exceptions.throwIfFatal(var9);
                        if (errors == null) {
                            errors = new ArrayList();
                        }

                        errors.add(var9);
                    }
                }
            }

            if (errors != null) {
                if (errors.size() == 1) {
                    throw ExceptionHelper.wrapOrThrow((Throwable)errors.get(0));
                } else {
                    throw new CompositeException(errors);
                }
            }
        }
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public boolean add(@NonNull Disposable d) {
        ObjectHelper.requireNonNull(d, "d is null");
        if (!this.disposed) {
            synchronized(this) {
                if (!this.disposed) {
                    OpenHashSet<Disposable> set = this.resources;
                    if (set == null) {
                        set = new OpenHashSet();
                        this.resources = set;
                    }

                    set.add(d);
                    return true;
                }
            }
        }

        d.dispose();
        return false;
    }

    public boolean addAll(@NonNull Disposable... ds) {
        ObjectHelper.requireNonNull(ds, "ds is null");
        if (!this.disposed) {
            synchronized(this) {
                if (!this.disposed) {
                    OpenHashSet<Disposable> set = this.resources;
                    if (set == null) {
                        set = new OpenHashSet(ds.length + 1);
                        this.resources = set;
                    }

                    Disposable[] var11 = ds;
                    int var12 = ds.length;

                    for(int var6 = 0; var6 < var12; ++var6) {
                        Disposable d = var11[var6];
                        ObjectHelper.requireNonNull(d, "d is null");
                        set.add(d);
                    }

                    return true;
                }
            }
        }

        Disposable[] var2 = ds;
        int var3 = ds.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Disposable d = var2[var4];
            d.dispose();
        }

        return false;
    }

    public boolean remove(@NonNull Disposable d) {
        if (this.delete(d)) {
            d.dispose();
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(@NonNull Disposable d) {
        ObjectHelper.requireNonNull(d, "Disposable item is null");
        if (this.disposed) {
            return false;
        } else {
            synchronized(this) {
                if (this.disposed) {
                    return false;
                } else {
                    OpenHashSet<Disposable> set = this.resources;
                    return set != null && set.remove(d);
                }
            }
        }
    }

    public void clear() {
        if (!this.disposed) {
            OpenHashSet set;
            synchronized(this) {
                if (this.disposed) {
                    return;
                }

                set = this.resources;
                this.resources = null;
            }

            this.dispose(set);
        }
    }

    public int size() {
        if (this.disposed) {
            return 0;
        } else {
            synchronized(this) {
                if (this.disposed) {
                    return 0;
                } else {
                    OpenHashSet<Disposable> set = this.resources;
                    return set != null ? set.size() : 0;
                }
            }
        }
    }
}

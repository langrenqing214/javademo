package com.thundersoft.model.base;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.OpenHashSet;

public abstract class CompletableUseCase {
    private final static String TAG = "CompletableUseCase";
    private final Scheduler mBackgroundExecutor;
    private final Scheduler mScheduledExecutor;
    private final CompositeDisposableWrapper mDisposables;
    private CompositeDisposable mOuterDisposables = null;

    public CompletableUseCase(Scheduler backgroundExecutor, Scheduler scheduledExecutor) {
        this.mBackgroundExecutor = backgroundExecutor;
        this.mScheduledExecutor = scheduledExecutor;
        this.mDisposables = new CompositeDisposableWrapper();
    }

    public CompletableUseCase(Scheduler backgroundExecutor, Scheduler scheduledExecutor, @NonNull CompositeDisposable outerDisposables) {
        this.mBackgroundExecutor = backgroundExecutor;
        this.mScheduledExecutor = scheduledExecutor;
        this.mDisposables = new CompositeDisposableWrapper();
        this.mOuterDisposables = outerDisposables;
    }

    protected abstract Completable setupCompletable();

    public <T extends Disposable & CompletableObserver> void execute(@NonNull T observer) {
        Completable completable = this.setupCompletable().subscribeOn(this.mBackgroundExecutor).observeOn(this.mScheduledExecutor);
        this.addDisposable((Disposable)completable.subscribeWith(observer));
    }

    public void execute() {
        Completable completable = this.setupCompletable().subscribeOn(this.mBackgroundExecutor).observeOn(this.mScheduledExecutor);
        this.addDisposable(completable.subscribe());
    }

    public Completable chain() {
        return this.setupCompletable();
    }

    public void clear() {
        if (this.mOuterDisposables != null && this.mDisposables != null) {
            OpenHashSet<Disposable> resources = this.mDisposables.getResources();
            if (resources != null && resources.size() != 0) {
                Object[] array = resources.keys();
                Object[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Object disposable = var3[var5];
                    if (disposable instanceof Disposable) {
                        this.mOuterDisposables.remove((Disposable)disposable);
                    }
                }
            }
        }

        this.mDisposables.clear();
    }

    private void addDisposable(@NonNull Disposable disposable) {
        this.mDisposables.add(disposable);
        if (this.mOuterDisposables != null && disposable != null) {
            this.mOuterDisposables.add(disposable);
        }

    }
}

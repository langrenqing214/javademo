package com.thundersoft.model.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.OpenHashSet;

public abstract class ObservableUseCase<ReturnType> {
    private final static String TAG = "ObservableUseCase";
    private final Scheduler mBackgroundExecutor;
    private final Scheduler mScheduledExecutor;
    private final CompositeDisposableWrapper mDisposables;
    private CompositeDisposable mOuterDisposables = null;

    public ObservableUseCase(Scheduler backgroundExecutor, Scheduler scheduledExecutor) {
        this.mBackgroundExecutor = backgroundExecutor;
        this.mScheduledExecutor = scheduledExecutor;
        this.mDisposables = new CompositeDisposableWrapper();
    }

    public ObservableUseCase(Scheduler backgroundExecutor, Scheduler scheduledExecutor, @NonNull CompositeDisposable outerDisposables) {
        this.mBackgroundExecutor = backgroundExecutor;
        this.mScheduledExecutor = scheduledExecutor;
        this.mDisposables = new CompositeDisposableWrapper();
        this.mOuterDisposables = outerDisposables;
    }

    protected abstract Observable<ReturnType> setupObservable();

    public <T extends Disposable & Observer<ReturnType>> void execute(@NonNull T observer) {
        if (observer == null) {
            throw new NullPointerException("Cannot execute with a null observerCall void execute() if no observer is needed.");
        } else {
            Observable<ReturnType> observable = this.setupObservable().subscribeOn(this.mBackgroundExecutor).observeOn(this.mScheduledExecutor);
            this.addDisposable((Disposable)observable.subscribeWith(observer));
        }
    }

    public Observable<ReturnType> chain() {
        return this.setupObservable();
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
        if (disposable == null) {
            throw new NullPointerException("Cannot add null disposable");
        } else {
            this.mDisposables.add(disposable);
            if (this.mOuterDisposables != null && disposable != null) {
                this.mOuterDisposables.add(disposable);
            }

        }
    }

    /** @deprecated */
    @Deprecated
    public void execute() {
        Observable<ReturnType> observable = this.setupObservable().subscribeOn(this.mBackgroundExecutor).observeOn(this.mScheduledExecutor);
        this.addDisposable(observable.subscribe());
    }
}

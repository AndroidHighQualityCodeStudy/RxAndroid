/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactivex.android.samples;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Callable;

public class MainActivity extends Activity {
    private static final String TAG = "RxAndroidSamples";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.button_run_scheduler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion01();
            }
        });
    }


    void funcion01() {


        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                Log.e("xiaxl: ", "---subscribe---");


                emitter.onNext(0);
                emitter.onComplete();


                Log.e("xiaxl: ", "Thread Name: " + Thread.currentThread().getName());
                Log.e("xiaxl: ", "Thread ID: " + Thread.currentThread().getId());
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        Log.e("xiaxl: ", "onSubscribe beforeRequst");
                        Log.e("xiaxl: ", "Thread Name: " + Thread.currentThread().getName());
                        Log.e("xiaxl: ", "Thread ID: " + Thread.currentThread().getId());

                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {

                        Log.e("xiaxl: ", "onNext");
                        Log.e("xiaxl: ", "Thread Name: " + Thread.currentThread().getName());
                        Log.e("xiaxl: ", "Thread ID: " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("xiaxl: ", "onError");
                        Log.e("xiaxl: ", "Thread Name: " + Thread.currentThread().getName());
                        Log.e("xiaxl: ", "Thread ID: " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onComplete() {
                        //由于Reactive-Streams的兼容性，方法onCompleted被重命名为onComplete
                        Log.e("xiaxl: ", "onComplete");
                        Log.e("xiaxl: ", "Thread Name: " + Thread.currentThread().getName());
                        Log.e("xiaxl: ", "Thread ID: " + Thread.currentThread().getId());
                    }
                });


    }

}

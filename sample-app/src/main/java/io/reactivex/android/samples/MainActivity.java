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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
                // IO线程
                // 请求网络数据
                emitter.onNext(0);
                emitter.onComplete();


            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        // 主线程
                        // 更新 UI
                    }

                    @Override
                    public void onError(Throwable t) {
                        // 主线程
                        // 错误 UI
                    }

                    @Override
                    public void onComplete() {
                        // 主线程
                        // 更新 UI
                    }
                });
    }


    void funcion02() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // IO 线程
                // 请求网络数据
                e.onNext("123456");
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                // IO 线程
                // 网络数据解析(数据转化)
                //
                // throw new RequestFailException("获取网络请求失败");
                return 123;
            }
        }).doOnNext(new Consumer<Integer>() {    //保存登录结果UserInfo
            @Override
            public void accept(@NonNull Integer bean) throws Exception {
                // IO 线程
                // 保存网络数据

            }
        }).subscribeOn(Schedulers.io())   //IO线程
                .observeOn(AndroidSchedulers.mainThread())  //主线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer bean) throws Exception {
                        // 更新UI
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        // 错误 显示错误页面
                    }
                });
    }

}

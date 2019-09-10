package app.pranavjayaraj.apod;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import app.pranavjayaraj.apod.Database.AppDAO;
import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.Network.RetrofitService;
import app.pranavjayaraj.apod.Util.DateUtil;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pranav.
 */

public class Repository {

    private RetrofitService retrofitService;
    private AppDAO appDao;

    public Repository(RetrofitService retrofitService, AppDAO appDao){
        this.retrofitService = retrofitService;
        this.appDao = appDao;
    }

    public LiveData<List<Image>> getPictureList(){
        return appDao.getAll();
    }

    public void retrieveLatestData(final RepositoryCallbacks repositoryCallbacks){
        Log.d("RX", "retrieveDataWithRX called");
        Single<List<Image>> observablePictureList =
                retrofitService.getLatestPicture()
                        .flatMap(new Function<Image, SingleSource<List<Image>>>() {
                            @Override
                            public SingleSource<List<Image>> apply(Image picture) {
                                String endDate = picture.getDate();
                                String startDate = DateUtil.subtractDays(endDate, 25);
                                return retrofitService.getPicturesFromDateRange(startDate, endDate);
                            }
                        })
                        .flatMap(new Function<List<Image>, SingleSource<List<Image>>>() {
                            @Override
                            public SingleSource<List<Image>> apply(List<Image> pictureList) {
                                List<Image> processedPictureList = processPictures(pictureList);
                                return Single.just(processedPictureList);
                            }
                        });

        observablePictureList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .retry(2)
                .subscribe(new SingleObserver<List<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("RX", "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onSuccess(List<Image> pictureList) {
                        Log.d("RX", "onSuccess: " + pictureList.get(0).getDate() + " " + pictureList.get(pictureList.size()-1).getDate());
                        appDao.insertAll(pictureList);
                        repositoryCallbacks.onResponse("success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RX", "onSubscribe: " + e.getMessage());
                        repositoryCallbacks.onFailure(e);
                    }
                });

    }

    public void loadMoreData(String lastVisibleDate){
        String endDate = lastVisibleDate;
        String startDate = DateUtil.subtractDays(endDate, 15);

        Single<List<Image>> pictureListSingle =
                retrofitService.getPicturesFromDateRange(startDate, endDate)
                            .flatMap(new Function<List<Image>, SingleSource<? extends List<Image>>>() {
                            @Override
                            public SingleSource<? extends List<Image>> apply(List<Image> pictureList) {
                                List<Image> processedList = processPictures(pictureList);
                                return Single.just(processedList);
                            }
                        });

        pictureListSingle
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .retry(2)
                .subscribe(new SingleObserver<List<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("RX", "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onSuccess(List<Image> pictureList) {
                        Log.d("RX", "onSuccess: " + pictureList.get(0).getDate() + " " + pictureList.get(pictureList.size()-1).getDate());
                        appDao.insertAll(pictureList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RX", "onSubscribe: " + e.getMessage());
                    }
                });
    }

    private List<Image> processPictures(List<Image> pictures){
        Collections.reverse(pictures);
        for (Iterator<Image> iterator = pictures.iterator(); iterator.hasNext();) {
            Image picture = iterator.next();
            if(picture.getMediaType().equals("video")){
                iterator.remove();
            }
        }
        return pictures;
    }

    public void clearDatabase() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                appDao.deleteAll();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}

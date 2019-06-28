package com.zx.haijixing.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.zx.haijixing.HaiApplication;
import com.zx.haijixing.HaiNativeHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2018/9/6.
 */
public class OSSUploadHelper {


    private final static String OSS_IMG = "data/img/mobile/android/";
    private final static String OSS_VIDEO = "data/video/mobile/android/";

    private static OSSUploadHelper ossUploadHelper;
    /**
     * 上传client
     */
    OSS oss;
    /**
     * 成功上传(本地文件名作为key,阿里云地址为value)
     */
    Map<String, String> success = new HashMap<>();
    List<String> su = new ArrayList<String>();
    /**
     * 失败上传(返回失败文件的本地地址)
     */
    List<String> failure = new ArrayList<>();
    /**
     * 上传回调
     */
    UploadListener uploadListener;
    /**
     * 上传任务列表
     */
    List<OSSAsyncTask> ossAsyncTasks = new ArrayList<>();
    /**
     * 自动更新Token

     */
    OSSCredentialProvider credentialProvider = null;
    //数字进度回调
    INumberChangeInter iNumberChangeInter;
    String type;
    //上传次数
    AtomicLong atomicLong  = null;
    /**
     * 模式
     * @return
     */
    public static OSSUploadHelper getInstance(String token,String type) {
        if (ossUploadHelper == null) {
            ossUploadHelper = new OSSUploadHelper(token,type);
        }
        return ossUploadHelper;
    }

    /**
     * 构造函数
     */
    public OSSUploadHelper(String token, String type) {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(20 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(20 * 1000); // socket超时，默认15秒_
        conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(3);// 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        this.type = type;

        credentialProvider= new OSSAuthCredentialsProvider(HaiNativeHelper.ossToken() +"?_token="+token);
        oss = new OSSClient(HaiApplication.getInstance(), HaiNativeHelper.ossUrl(), credentialProvider, conf);
    }

    /**
     * 添加上传任务
     *
     * @param paths
     * @param listener
     */
    public void setDatas(final List<String> paths, UploadListener listener, final INumberChangeInter iNumberChangeInter) {
        this.uploadListener = listener;
        this.iNumberChangeInter = iNumberChangeInter;
        ossAsyncTasks.clear();

        su.clear();
        atomicLong = new AtomicLong(0);

        failure.clear();
        for (String path : paths) {
            final File file = new File(path);
            // 文件后缀
            String fileSuffix = "";
            if (file.isFile()) {
                // 获取文件后缀名
                fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
            }
            // 文件标识符objectKey
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR)+"";
            String month = calendar.get(Calendar.MONTH)+1+"";
            String day = calendar.get(Calendar.DATE)+"";
            Random random = new Random();
            int numbers = random.nextInt(900)+100;
            String objectKey = null;
            String order = year+"/"+month+"/"+day+"/"+ System.currentTimeMillis()+ numbers + fileSuffix;
            if (fileSuffix.equals(".mp4")||fileSuffix.equals(".avi")||fileSuffix.equals(".flv")){
                objectKey = OSS_VIDEO + type +"/"+order;
            }else {
                objectKey = OSS_IMG +type+"/"+ order;
            }

            //final String objectKey = ApiHelper.OSS_IMG + System.currentTimeMillis() + fileSuffix;
            /**
             * 用户自定义参数
             */
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.addUserMetadata("filePath", file.getPath());
            objectMetadata.addUserMetadata("fileName", file.getName());
            objectMetadata.addUserMetadata("objectKey", objectKey);


            PutObjectRequest put = new PutObjectRequest(HaiNativeHelper.ossBuck(), objectKey, file.getPath());
            //put.setProgressCallback(ossProgressCallback);
            /**
             * 上传任务
             */
            OSSAsyncTask task;
            task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                    long numberTwo = atomicLong.incrementAndGet();
                    Log.i("<<<oss sucess","<num>"+numberTwo);
                    iNumberChangeInter.onNumberChange((int)numberTwo);
                    String aliPath = formAliPath(putObjectRequest);
                    su.add(aliPath);
                    if (numberTwo == paths.size()) {
                        uploadListener.onUploadComplete(su, failure);
                    }
                }

                @Override
                public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                    long numberTwo = atomicLong.incrementAndGet();
                    Log.i("<<<oss defeat","<num>"+numberTwo);
                    failure.add("defeat"+numberTwo);
                    if (numberTwo == paths.size()) {
                        uploadListener.onUploadComplete(su, failure);
                    }
                    // 请求异常
                    if (e != null) {
                        // 本地异常如网络异常等
                        Log.i("<<OSS","网络异常"+ e.getMessage());
                    }
                    if (e1 != null) {
                        // 服务异常
                        Log.i("<<OSS","服务器异常");
                        Log.e("<<OSS", e1.getErrorCode());
                        Log.e("<<OSS", e1.getRequestId());
                        Log.e("<<OSS", e1.getHostId());
                        Log.e("<<OSS", e1.getRawMessage());
                    }
                }
            });
            /**
             * 添加到上传记录
             */
            ossAsyncTasks.add(task);
        }
    }

    /**
     * 添加上传任务
     * @param paths key:本地地址
     * @param listener
     */
   public void setDatas(final Map<String,String>paths, final UploadListener listener,final INumberChangeInter iNumberChangeInter){
        this.uploadListener=listener;
        this.iNumberChangeInter = iNumberChangeInter;
        ossAsyncTasks.clear();
        atomicLong = new AtomicLong(0);
        success.clear();
        failure.clear();
       Iterator<Map.Entry<String, String>> iterator1 = paths.entrySet().iterator();
       while (iterator1.hasNext()){
           Map.Entry<String, String> next = iterator1.next();
           final String key = next.getKey();
           final String path = next.getValue();
           File file=new File(path);
           Log.i("<<<oss helper","<<<file.getPath()>"+file.getPath());
            // 文件后缀
            String fileSuffix = "";
           //文件路径
            if (file.isFile()) {
                // 获取文件后缀名
                fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
            }
            // 文件标识符objectKey
           Calendar calendar = Calendar.getInstance();
           String year = calendar.get(Calendar.YEAR)+"";
           String month = calendar.get(Calendar.MONTH)+1+"";
           String day = calendar.get(Calendar.DATE)+"";
           Random random = new Random();
           int numbers = random.nextInt(900)+100;
           String objectKey = null;
           String order = year+"/"+month+"/"+day+"/"+ System.currentTimeMillis()+ numbers + fileSuffix;
           if (fileSuffix.equals(".mp4")||fileSuffix.equals(".avi")||fileSuffix.equals(".flv")){
               objectKey = OSS_VIDEO + type +"/"+order;
           }else {
               objectKey = OSS_IMG +type+"/" + order;
           }
           ObjectMetadata objectMetadata=new ObjectMetadata();
           objectMetadata.addUserMetadata("fileKey",key+"");
           objectMetadata.addUserMetadata("objectKey", objectKey);

            final PutObjectRequest put=new PutObjectRequest(HaiNativeHelper.ossBuck(),objectKey,file.getPath(),objectMetadata);

            OSSAsyncTask task;
            task=oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                    long number = atomicLong.incrementAndGet();
                    Log.i("<<<oss sucess","<num>"+number);
                    iNumberChangeInter.onNumberChange((int)number);
                    String aliPath = formAliPath(putObjectRequest);
                    String fileKey = putObjectRequest.getMetadata().getUserMetadata().get("fileKey");
                    success.put(fileKey,aliPath);
                    if (number==paths.size()){
                        uploadListener.onUploadComplete(success,failure);
                    }
                }

                @Override
                public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                    long number = atomicLong.incrementAndGet();
                    Log.i("<<<oss defeat","<num>"+number);
                    failure.add(putObjectRequest.getMetadata().getUserMetadata().get("fileKey"));
                    if (number==paths.size()){
                        uploadListener.onUploadComplete(success,failure);
                    }
                }
            });
            ossAsyncTasks.add(task);
        }

    }

    public void cancleTasks() {
        for (OSSAsyncTask task : ossAsyncTasks) {
            if (task.isCompleted()) {

            }else {
                task.cancel();
            }
        }
    }

    /**
     * 拼接远程访问地址
     *
     * @param putObjectRequest
     * @return
     */
    private String formAliPath(PutObjectRequest putObjectRequest) {
        return putObjectRequest.getObjectKey();
    }

}

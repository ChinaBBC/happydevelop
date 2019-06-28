package com.zx.haijixing.oss;

import java.util.List;
import java.util.Map;

/**
 * Created by zx on 2018/9/6.
 * 上传监听
 */

public interface UploadListener {
    /**
     * 上传完成
     *
     * @param success
     * @param failure
     */
    void onUploadComplete(Map<String, String> success, List<String> failure);
    void onUploadComplete(List<String> success, List<String> failure);
}

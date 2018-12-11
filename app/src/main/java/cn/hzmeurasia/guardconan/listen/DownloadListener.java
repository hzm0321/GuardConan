package cn.hzmeurasia.guardconan.listen;

/**
 * 类名: DownloadListener<br>
 * 功能:(下载接口)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/10 16:28
 */
public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}

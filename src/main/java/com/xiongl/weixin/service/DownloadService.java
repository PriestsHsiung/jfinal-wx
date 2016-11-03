package com.xiongl.weixin.service;

import com.google.gson.Gson;

import com.heyuo.qy.model.ConsultRecords;
import com.heyuo.qy.model.MsgMedia;
import com.xiongl.weixin.sdk.Constant;
import com.xiongl.weixin.sdk.ErrorResult;
import com.xiongl.weixin.sdk.MediaFile;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import com.xiongl.weixin.sdk.MediaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016-11-2.
 */
public class DownloadService {
    private static final String PATH = "D:/qywx/";
    private static MediaApi mediaApi = RetrofitService.getInstance(false).create(MediaApi.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaApi.class);

    public boolean download(final String type, final String msgId, final String content) {
        Call<ResponseBody> call;
        if ("image".equals(type)) {
            call = mediaApi.getImageFile(content);
        } else {
            String accessToken = AccessTokenService.getInstance().getAccessToken(Constant.CONSULT_APP);
            call = mediaApi.getVoiceFile(accessToken, content);
        }

        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();

                    MediaType mediaType = body.contentType();

                    MediaFile mediaFile = new MediaFile();
                    if (mediaType.subtype().equals("text") || mediaType.subtype().equals("json")) {
                        Gson g = new Gson();
                        ErrorResult er = g.fromJson(body.string(), ErrorResult.class);
                        if (er.getErrcode() == Constant.INVALID_ACCESS_TOKEN) {
                            AccessTokenService.getInstance().refreshAccessToken(Constant.CONSULT_APP);
                            download(type, msgId, content);
                        } else {
                            updateCrawlStatus(msgId, er.getErrcode() + ":" + er.getErrmsg());
                        }
                    } else {
                        String ds = response.headers().get("Content-disposition");
                        String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
                        String relName = fullName.substring(0, fullName.lastIndexOf("."));
                        String suffix = fullName.substring(relName.length() + 1);

                        mediaFile.setFullName(fullName);
                        mediaFile.setFileName(relName);
                        mediaFile.setSuffix(suffix);
                        mediaFile.setContentLength(body.contentLength() + "");
                        mediaFile.setContentType(body.contentType().toString());
                        mediaFile.setFileStream(body.byteStream());

                        String path = currentPath(mediaFile);
                        Boolean res = writeResponseBodyToDisk(path, mediaFile);
                        if (res) {
                            MsgMedia mm = new MsgMedia();
                            mm.setMsgId(msgId);
                            mm.setFilePath(path);
                            mm.save();
                            updateCrawlStatus(msgId, "");
                        }
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                LOGGER.error(throwable.getMessage());
            }

        });

        return true;
    }

    private String currentPath(MediaFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        String folder = PATH + date + "/";
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String fileName = System.currentTimeMillis() + "." + file.getContentType();
        return  folder + fileName;
    }

    private void updateCrawlStatus(String msgId, String errMsg) {
        ConsultRecords cr = ConsultRecords.dao.findFirst("select * from consult_records where msg_id = ?", msgId);
        if (null != cr) {
            cr.setCrawl(true);
            cr.setCrawlErrorMsg(errMsg);
            cr.update();
        }
    }

    private boolean writeResponseBodyToDisk(String path, MediaFile file) {
        try {
            File futureStudioIconFile = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;


            try {
                byte[] fileReader = new byte[4096];

                inputStream = file.getFileStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                LOGGER.error("保存文件失败，路径" + path + "错误:" + e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            LOGGER.error("保存文件失败，路径" + path + "错误:" + e.getMessage());
            return false;
        }
    }
}

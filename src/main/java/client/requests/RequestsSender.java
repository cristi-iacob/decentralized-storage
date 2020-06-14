package client.requests;

import client.GlobalVariables;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class RequestsSender {
    public CloseableHttpResponse loginRequest(String username, String password) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GlobalVariables.URI + "login");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CloseableHttpResponse registerRequest(String username, String password) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost  post = new HttpPost(GlobalVariables.URI + "register");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CloseableHttpResponse uploadRequest(File file, String username) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GlobalVariables.URI + "upload");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("client", username, ContentType.TEXT_PLAIN);

        try {
            builder.addBinaryBody("file", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM, file.getName());
            HttpEntity  multipart = builder.build();
            post.setEntity(multipart);
            CloseableHttpResponse response = client.execute(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CloseableHttpResponse downloadRequest(String filename, String username) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GlobalVariables.URI + "download");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client", username));
        params.add(new BasicNameValuePair("filename", filename));
        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CloseableHttpResponse logoutRequest(String username) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GlobalVariables.URI + "logout");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

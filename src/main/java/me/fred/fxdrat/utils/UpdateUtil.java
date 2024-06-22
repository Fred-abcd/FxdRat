package me.fred.fxdrat.utils;

import me.fred.fxdrat.FxdRat;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class UpdateUtil {

    private static final String DOWNLOAD_PATH = System.getProperty("user.dir") + File.separator + "FxdRat.jar";

    public static void checkForUpdates() {
        String latestVersion = null;
        try {
            latestVersion = getLatestReleaseVersion();
            if (!FxdRat.version.equalsIgnoreCase(latestVersion)) {
                Logger.log("Update available! Latest version: " + latestVersion, LogLevel.INFO);
                Logger.log("Updating...", LogLevel.INFO);
                downloadAndRunLatestVersion();
            } else {
                Logger.log("You are using the latest version: " + FxdRat.version, LogLevel.INFO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getLatestReleaseVersion() throws IOException {
        URL url = new URL("https://api.github.com/repos/Fred-abcd/FxdRat/releases/latest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        JSONObject json = new JSONObject(content.toString());
        return json.getString("tag_name");
    }

    private static void downloadAndRunLatestVersion() throws Exception {
        String downloadUrl = getLatestReleaseDownloadUrl();
        downloadFile(downloadUrl, DOWNLOAD_PATH);
        runJarFile(DOWNLOAD_PATH);
    }

    private static String getLatestReleaseDownloadUrl() throws Exception {
        URL url = new URL("https://api.github.com/repos/Fred-abcd/FxdRat/releases/latest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        JSONObject json = new JSONObject(content.toString());
        return json.getJSONArray("assets").getJSONObject(0).getString("browser_download_url");
    }

    private static void downloadFile(String fileUrl, String destinationPath) throws IOException {
        URL url = new URL(fileUrl);
        FileUtils.copyURLToFile(url, new File(destinationPath));
    }

    private static void runJarFile(String newJarFilePath) throws IOException, URISyntaxException {
        String currentJarFilePath = UpdateUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", currentJarFilePath);
        pb.directory(new File(System.getProperty("user.dir")));
        Process p = pb.start();
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

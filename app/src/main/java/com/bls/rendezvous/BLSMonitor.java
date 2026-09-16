package com.bls.rendezvous;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BLSMonitor {

    // =========================================
    // BLS OFFICIAL URLs
    // =========================================

    public static final String ALGIERS_URL =
            "https://algeria.blsspainvisa.com/algiers/";

    public static final String ORAN_URL =
            "https://algeria.blsspainvisa.com/oran/";

    // =========================================
    // MONITORING INTERVAL
    // =========================================

    public static final long CHECK_INTERVAL_MS =
            45 * 1000L;

    // =========================================
    // RESULT STATUS
    // =========================================

    public enum Status {

        AVAILABLE,

        NO_APPOINTMENT,

        WEBSITE_OK,

        WEBSITE_UNAVAILABLE,

        ACCESS_BLOCKED,

        TOO_MANY_REQUESTS,

        SERVER_ERROR,

        CONNECTION_ERROR,

        UNKNOWN_ERROR
    }

    // =========================================
    // RESULT OBJECT
    // =========================================

    public static class Result {

        private final Status status;

        private final int httpCode;

        private final String message;

        private final long responseTime;

        public Result(
                Status status,
                int httpCode,
                String message,
                long responseTime
        ) {

            this.status = status;
            this.httpCode = httpCode;
            this.message = message;
            this.responseTime = responseTime;
        }

        public Status getStatus() {
            return status;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public String getMessage() {
            return message;
        }

        public long getResponseTime() {
            return responseTime;
        }
    }

    // =========================================
    // CHECK BLS
    // =========================================

    public Result check(String center) {

        String url;

        if (center == null) {

            url = ALGIERS_URL;

        } else if (
                center.equalsIgnoreCase("oran")
        ) {

            url = ORAN_URL;

        } else {

            url = ALGIERS_URL;
        }

        long startTime =
                System.currentTimeMillis();

        HttpURLConnection connection =
                null;

        try {

            URL target =
                    new URL(url);

            connection =
                    (HttpURLConnection)
                            target.openConnection();

            connection.setRequestMethod(
                    "GET"
            );

            connection.setConnectTimeout(
                    15000
            );

            connection.setReadTimeout(
                    15000
            );

            connection.setInstanceFollowRedirects(
                    true
            );

            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Android) BLS-Rendez-Vous"
            );

            connection.setRequestProperty(
                    "Accept",
                    "text/html,application/xhtml+xml"
            );

            connection.setRequestProperty(
                    "Accept-Language",
                    "en-US,en;q=0.9"
            );

            int responseCode =
                    connection.getResponseCode();

            long responseTime =
                    System.currentTimeMillis()
                            - startTime;

            // =================================
            // SUCCESS
            // =================================

            if (
                    responseCode >= 200
                            &&
                    responseCode < 300
            ) {

                String page =
                        readResponse(
                                connection.getInputStream()
                        );

                /*
                 * IMPORTANT:
                 *
                 * A successful HTTP response does NOT
                 * mean that an appointment exists.
                 *
                 * The real appointment parser will be
                 * added after we identify the current
                 * BLS booking data source.
                 */

                return new Result(
                        Status.WEBSITE_OK,
                        responseCode,
                        "BLS website is reachable",
                        responseTime
                );
            }

            // =================================
            // REDIRECTION
            // =================================

            if (
                    responseCode >= 300
                            &&
                    responseCode < 400
            ) {

                return new Result(
                        Status.WEBSITE_OK,
                        responseCode,
                        "BLS redirected the request",
                        responseTime
                );
            }

            // =================================
            // ACCESS BLOCKED
            // =================================

            if (
                    responseCode == 401
                            ||
                    responseCode == 403
            ) {

                return new Result(
                        Status.ACCESS_BLOCKED,
                        responseCode,
                        "BLS access requires verification",
                        responseTime
                );
            }

            // =================================
            // TOO MANY REQUESTS
            // =================================

            if (
                    responseCode == 429
            ) {

                return new Result(
                        Status.TOO_MANY_REQUESTS,
                        responseCode,
                        "BLS rate limit detected",
                        responseTime
                );
            }

            // =================================
            // SERVER ERROR
            // =================================

            if (
                    responseCode >= 500
            ) {

                return new Result(
                        Status.SERVER_ERROR,
                        responseCode,
                        "BLS server is temporarily unavailable",
                        responseTime
                );
            }

            // =================================
            // UNKNOWN HTTP RESULT
            // =================================

            return new Result(
                    Status.UNKNOWN_ERROR,
                    responseCode,
                    "Unexpected BLS response",
                    responseTime
            );

        } catch (java.net.SocketTimeoutException e) {

            long responseTime =
                    System.currentTimeMillis()
                            - startTime;

            return new Result(
                    Status.WEBSITE_UNAVAILABLE,
                    -1,
                    "BLS connection timed out",
                    responseTime
            );

        } catch (java.net.UnknownHostException e) {

            long responseTime =
                    System.currentTimeMillis()
                            - startTime;

            return new Result(
                    Status.CONNECTION_ERROR,
                    -1,
                    "Internet connection unavailable",
                    responseTime
            );

        } catch (Exception e) {

            long responseTime =
                    System.currentTimeMillis()
                            - startTime;

            return new Result(
                    Status.UNKNOWN_ERROR,
                    -1,
                    "BLS check failed",
                    responseTime
            );

        } finally {

            if (connection != null) {

                connection.disconnect();
            }
        }
    }

    // =========================================
    // READ RESPONSE
    // =========================================

    private String readResponse(
            InputStream inputStream
    ) {

        if (inputStream == null) {

            return "";
        }

        StringBuilder result =
                new StringBuilder();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    inputStream
                            )
                    );

            String line;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                result.append(line);
                result.append("\n");
            }

            reader.close();

        } catch (Exception ignored) {

            // Response body is not required
            // for the basic website check.
        }

        return result.toString();
    }
}

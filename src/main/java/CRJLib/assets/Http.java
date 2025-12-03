/*
 * Copyright (c) 2025 CJ Remillard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package CRJLib.assets;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Http - HTTP Client Utility Class
 * Provides simple HTTP request functionality for common web operations.
 *
 * <p>Features:
 * <ul>
 * <li>GET, POST, PUT, DELETE requests</li>
 * <li>Custom headers support</li>
 * <li>Timeout configuration</li>
 * <li>Response status and body handling</li>
 * <li>URL encoding utilities</li>
 * </ul>
 *
 * @author CJ Remillard
 * @version 1.0
 */
public class Http
{
    private int connectTimeout = 10000;  // 10 seconds
    private int readTimeout = 10000;     // 10 seconds
    private Map<String, String> defaultHeaders = new HashMap<>();

    /**
     * Sets the connection timeout in milliseconds.
     *
     * @param milliseconds Timeout in milliseconds
     */
    public void setConnectTimeout(int milliseconds) {
        this.connectTimeout = milliseconds;
    }

    /**
     * Sets the read timeout in milliseconds.
     *
     * @param milliseconds Timeout in milliseconds
     */
    public void setReadTimeout(int milliseconds) {
        this.readTimeout = milliseconds;
    }

    /**
     * Adds a default header that will be included in all requests.
     *
     * @param key Header name
     * @param value Header value
     */
    public void addDefaultHeader(String key, String value) {
        defaultHeaders.put(key, value);
    }

    /**
     * Performs an HTTP GET request.
     *
     * @param urlString The URL to request
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse get(String urlString) throws IOException {
        return get(urlString, null);
    }

    /**
     * Performs an HTTP GET request with custom headers.
     *
     * @param urlString The URL to request
     * @param headers Custom headers (can be null)
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse get(String urlString, Map<String, String> headers) throws IOException {
        return sendRequest(urlString, "GET", null, headers);
    }

    /**
     * Performs an HTTP POST request.
     *
     * @param urlString The URL to request
     * @param body The request body
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse post(String urlString, String body) throws IOException {
        return post(urlString, body, null);
    }

    /**
     * Performs an HTTP POST request with custom headers.
     *
     * @param urlString The URL to request
     * @param body The request body
     * @param headers Custom headers (can be null)
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse post(String urlString, String body, Map<String, String> headers) throws IOException {
        return sendRequest(urlString, "POST", body, headers);
    }

    /**
     * Performs an HTTP PUT request.
     *
     * @param urlString The URL to request
     * @param body The request body
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse put(String urlString, String body) throws IOException {
        return put(urlString, body, null);
    }

    /**
     * Performs an HTTP PUT request with custom headers.
     *
     * @param urlString The URL to request
     * @param body The request body
     * @param headers Custom headers (can be null)
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse put(String urlString, String body, Map<String, String> headers) throws IOException {
        return sendRequest(urlString, "PUT", body, headers);
    }

    /**
     * Performs an HTTP DELETE request.
     *
     * @param urlString The URL to request
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse delete(String urlString) throws IOException {
        return delete(urlString, null);
    }

    /**
     * Performs an HTTP DELETE request with custom headers.
     *
     * @param urlString The URL to request
     * @param headers Custom headers (can be null)
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    public HttpResponse delete(String urlString, Map<String, String> headers) throws IOException {
        return sendRequest(urlString, "DELETE", null, headers);
    }

    /**
     * Sends an HTTP request with the specified method, body, and headers.
     *
     * @param urlString The URL to request
     * @param method HTTP method (GET, POST, PUT, DELETE, etc.)
     * @param body Request body (can be null for GET/DELETE)
     * @param headers Custom headers (can be null)
     * @return HttpResponse containing status code and body
     * @throws IOException If the request fails
     */
    private HttpResponse sendRequest(String urlString, String method, String body, Map<String, String> headers)
            throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Configure connection
            connection.setRequestMethod(method);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);

            // Set default headers
            for (Map.Entry<String, String> header : defaultHeaders.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            // Set custom headers
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            // Send body if present
            if (body != null && !body.isEmpty()) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            // Read response
            int statusCode = connection.getResponseCode();
            String responseBody = readResponse(connection);

            return new HttpResponse(statusCode, responseBody);

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Reads the response from an HTTP connection.
     *
     * @param connection The HTTP connection
     * @return Response body as string
     * @throws IOException If reading fails
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        InputStream inputStream;

        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            // If getInputStream fails, try getErrorStream
            inputStream = connection.getErrorStream();
            if (inputStream == null) {
                throw e;
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line).append('\n');
            }

            return response.toString();
        }
    }

    /**
     * URL-encodes a string for safe use in URLs.
     *
     * @param value The string to encode
     * @return URL-encoded string
     */
    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            // This should never happen with UTF-8
            throw new RuntimeException(e);
        }
    }

    /**
     * URL-decodes a string.
     *
     * @param value The string to decode
     * @return URL-decoded string
     */
    public static String urlDecode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            // This should never happen with UTF-8
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a query string from a map of parameters.
     *
     * @param params Map of parameter names to values
     * @return Query string (e.g., "key1=value1&key2=value2")
     */
    public static String buildQueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder query = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!first) {
                query.append('&');
            }
            query.append(urlEncode(entry.getKey()));
            query.append('=');
            query.append(urlEncode(entry.getValue()));
            first = false;
        }

        return query.toString();
    }

    /**
     * HttpResponse - Represents an HTTP response.
     */
    public static class HttpResponse {
        private final int statusCode;
        private final String body;

        public HttpResponse(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        /**
         * Gets the HTTP status code.
         *
         * @return Status code (e.g., 200, 404, 500)
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * Gets the response body.
         *
         * @return Response body as string
         */
        public String getBody() {
            return body;
        }

        /**
         * Checks if the response was successful (status code 2xx).
         *
         * @return true if status code is 200-299
         */
        public boolean isSuccess() {
            return statusCode >= 200 && statusCode < 300;
        }

        @Override
        public String toString() {
            return "HttpResponse{statusCode=" + statusCode + ", body='" + body + "'}";
        }
    }
}

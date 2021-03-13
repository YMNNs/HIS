/*
 * MIT License
 *
 * Copyright (c) 2019. 杨梦博
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

package DAL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * JSON RW
 * TESTED
 */

class JsonUtil {
    static void hashSetToJson(HashSet hashSet, String filePath) {
        String[] jsonStr = new String[hashSet.size()];
        int i = 0;
        for (Object o : hashSet) {
            jsonStr[i] = JSON.toJSONString(o);
            i++;
        }
        writeFile(filePath, jsonStr);
    }

    static void objectToJson(Object O, String filePath) {
        String[] jsonStr = new String[1];
        jsonStr[0] = JSON.toJSONString(O);
        writeFile(filePath, jsonStr);
    }

    static String jsonToString(String filePath) {
        String readJson = readText(filePath);
        return readJson;
    }

    static String[] jsonToStringArray(String filePath) {
        String readJson = readText(filePath);
        try {
            return readJson.split("\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void hashTableToJson(Hashtable hashtable, String filePath) {
        String[] jsonStr = new String[hashtable.size()];
        int i = 0;
        for (Object o : hashtable.keySet()) {
            jsonStr[i] = JSON.toJSONString(o) + "&" + JSON.toJSONString(hashtable.get(o));
            i++;
        }
        writeFile(filePath, jsonStr);
    }

    static String[][] jsonToString2dArray(String filePath) {
        String readJson = readText(filePath);
        try {
            String[] line = readJson.split("\n");
            String[][] result = new String[line.length][2];
            for (int i = 0; i < line.length; i++) {
                if (line[i].contains("&")) {
                    result[i][0] = line[i].split("&")[0];
                    result[i][1] = line[i].split("&")[1];
                } else {
                    i++;
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readText(String filePath) {
        BufferedReader reader = null;
        String readJson = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                readJson = readJson + tempString + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return readJson;
    }

    private static void writeFile(String filePath, String[] jsonStr) {
        BufferedWriter writer = null;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));
            writer.write("");
            for (String s : jsonStr) {
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}





package uz.expense.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.expense.api.models.TableOptions;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static final Logger _logger = LogManager.getLogger(Utils.class);
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static <T> TypeToken<List<T>> listType() {
        return new TypeToken<List<T>>() {
        };
    }

    public static String getStringDigest(String password) {
        String s = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(password.getBytes());
            s = toHexString(digest.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            //
        }
        return s;
    }

    public static String getIsNullObject(String str) {
        if (str == null || str.equals("null")) {
            return null;
        }
        return str;
    }

    public static Integer getIsNullObject(Integer str) {
        if (str == null || str.equals("null")) {
            return null;
        }
        return str;
    }

    public static Date getIsNullObject(Date str) {
        if (str == null || str.equals("null")) {
            return null;
        }
        return str;
    }
    public static String isNullString(Object object) {
        return object == null ? "-" : object.toString();
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || (String.valueOf(obj)).trim().isEmpty();
    }

    private static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (byte aBlock : block) {
            byte2hex(aBlock, buf);
        }

        return buf.toString();
    }

    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'
        };
        int high = (b & 0xf0) >> 4;
        int low = b & 0xf;
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> generateParametersMap(Object... objs) {
        Map<String, Object> m = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        for (Object obj : objs) {
            if (obj instanceof MultivaluedMap) {
                MultivaluedMap<String, Object> mm = (MultivaluedMap<String, Object>) obj;
                for (Object o : mm.keySet()) {
                    String key = (String) o;
                    m.put(key, mm.getFirst(key));
                }
            } else if (obj instanceof TableOptions) {
                TableOptions tob = (TableOptions) obj;
                m.put("page", tob.getPage());
                m.put("perPage", tob.getPerPage());
                if (tob.getSortBy() != null) {
                    m.put("orderby_", (tob.getSortBy().toLowerCase().equals("id") ? "a.id" : tob.getSortBy()) + (tob.getDescending() ? " desc" : ""));
                }
            } else {
                m.putAll(om.convertValue(obj, Map.class));
            }

        }
        return m;
    }

    public static RowBounds parseRowBounds(Map<String, Object> params) {
        Integer page = Utils.parseInt(params.get("page"));
        Integer perPage = Utils.parseInt(params.get("perPage"));
        if (page == null || perPage == null) {
            return RowBounds.DEFAULT;
        }
        return new RowBounds((page - 1) * perPage, perPage);
    }

    public static Integer parseInt(Object obj) {
        return parseInt(obj, null);
    }

    public static Integer parseInt(Object obj, Integer defValue) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }
        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static Response toResponse() {
        return toResponse(null);
    }

    public static <T> Response toResponse(T object) {
        return Response
                .ok(object)
                .build();
    }

    public static String toString(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }


    public static Date convert(Long millis) {
        return new Date(millis);
    }

    public static String convertToString(Long millis) {
        if (millis == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(c.getTime());
    }

   public static Double extractNumber(String text, Double defValue) {
        if (StringUtils.isBlank(text)) {
            return defValue;
        }
        return parseDouble(text.replaceAll("[^0-9]", ""), defValue);
    }

    public static Double parseDouble(String a) {
        return parseDouble(a, null);
    }

    public static Double parseDouble(Object object, Double defValue) {
        if (object == null) {
            return defValue;
        }
        if (object instanceof Double) {
            return (Double) object;
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception ignored) {

        }
        return defValue;
    }

    public static String getUniquePSUEDOId() {
        String serial = "20210806", physycalAdress = "70-5A-0F-3C-84-F2";
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            physycalAdress = sb.toString();
        } catch (IOException ex) {

            System.out.println(ex);
        }

        String m_szDevIDShort = "20210806" + physycalAdress;
        try {
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "20210806";
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static String getFileExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}

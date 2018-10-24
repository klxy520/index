package com.jc.cz_index.common.tool.scaffold;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;
//import org.bouncycastle.util.encoders.UrlBase64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * String Utility Class
 */
public class StringUtil
{
	private static final char	CHAR_BLANK		= ' ';
	public final static String	COLON			= ":";
	public final static String	COMMA			= ",";
	public final static String	EMPTY			= "";
	public final static String	UNDER_LINE		= "_";
	public final static String	ENDL			= "\n";
	public final static String	SLASH			= "/";
	public final static String	BLANK			= " ";
	public final static String	DOT				= ".";
	public final static String	FILE_SEPARATOR	= File.separator;
	public final static String	KEY			= "*UHB%THN";

	static char					hexDigits[]		=
												{ // �������ֽ�ת���� 16
													// ���Ʊ�ʾ���ַ�
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	
	public static String getMD5(String data)
	{
		String s = "";
		byte[] source = data.getBytes();
		try
		{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 �ļ�������һ�� 128 λ�ĳ�����
			// ���ֽڱ�ʾ���� 16 ���ֽ�
			char str[] = new char[16 * 2]; // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ�
			// ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
			int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
			for (int i = 0; i < 16; i++)
			{ // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�
				// ת���� 16 �����ַ��ת��
				byte byte0 = tmp[i]; // ȡ�� i ���ֽ�
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // ȡ�ֽ��и� 4
															// λ������ת��,
				// >>> Ϊ�߼����ƣ������λһ������
				str[k++] = hexDigits[byte0 & 0xf]; // ȡ�ֽ��е� 4 λ������ת��
			}
			s = new String(str); // ����Ľ��ת��Ϊ�ַ�
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
	
	/**
     * Description 根据键值进行加密
     * @param data 
     * @return
     * @throws Exception
     */
	public static String DESEncrypt(String data) throws Exception {
    	
	       // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(KEY.getBytes());
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        String strs = new BASE64Encoder().encode( cipher.doFinal(data.getBytes()));
        return strs;
    }
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String DESDecrypt(String data) throws Exception,
            Exception {
        if (data == null)
            return null;
        byte[] buf = new BASE64Decoder().decodeBuffer(data);
        
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(KEY.getBytes());
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return new String(cipher.doFinal(buf));
    }
	
	private StringUtil()
	{
		// nothing
	}
	
	
	/**
	 * encode a password using MD5 algorithm
	 * 
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password)
	{
		String encodedPassword = null;
		try
		{
			encodedPassword = encodePassword(password, "MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return encodedPassword;
	}
	
	
	public static String changeList2String(List<String> list)
	{
		String tmp = list.toString();
		return tmp.substring(1, tmp.length() - 1);
	}
	
	
	/**
	 * check if the string is blank "" and null return true
	 * 
	 */
	public static boolean isBlank(String str)
	{
		boolean flag = false;
		if (str == null || "".equals(str.trim()))
		{
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * check if the String is not Blank "" and null return false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str)
	{
		boolean flag = false;
		if (str != null && !"".equals(str))
		{
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * check if the String is not blank "" and null and "" after trim() rerurn
	 * false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlankAfterTrim(String str)
	{
		boolean flag = false;
		if (str != null && !"".equals(str) && !"".equals(str.trim()))
		{
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodePassword(String password, String algorithm) throws NoSuchAlgorithmException
	{
		byte[] unencodedPassword = password.getBytes();
		
		MessageDigest md = null;
		
		// first create an instance, given the provider
		md = MessageDigest.getInstance(algorithm);
		
		md.reset();
		
		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);
		
		// now calculate the hash
		byte[] encodedPassword = md.digest();
		
		StringBuffer buf = new StringBuffer();
		
		for (int i = 0; i < encodedPassword.length; i++)
		{
			if ((encodedPassword[i] & 0xff) < 0x10)
			{
				buf.append("0");
			}
			
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		
		return buf.toString();
	}
	
	
	public static boolean isEmpty(String str)
	{
		if (str == null)
			return true;
		return EMPTY.equals(str) ? true : false;
	}
	
	
//	/**
//	 * Encode a string using Base64 encoding. Used when storing passwords as
//	 * cookies.
//	 * 
//	 * This is weak encoding in that anyone can use the decodeString routine to
//	 * reverse the encoding.
//	 * 
//	 * @param str
//	 * @return String
//	 */
//	public static String base64Encode(String str)
//	{
//		// sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//		// return encoder.encodeBuffer(str.getBytes()).trim();
//		byte[] bytes = UrlBase64.encode(str.getBytes());
//		return new String(bytes);
//	}
//	
//	
//	/**
//	 * Decode a string using Base64 encoding.
//	 * 
//	 * @param str
//	 * @return String
//	 */
//	public static String base64Decode(String str)
//	{
//		// sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
//		// try {
//		// return new String(dec.decodeBuffer(str));
//		// } catch (IOException io) {
//		// throw new RuntimeException(io.getMessage(), io.getCause());
//		// }
//		byte[] bytes = UrlBase64.decode(str.getBytes());
//		return new String(bytes);
//	}
	
	
	/**
	 * ��ȡָ�����ȵ�����ִ�
	 * 
	 * @param length
	 * @return String
	 */
	public static String getRandomString(int length)
	{
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int i = 0;
		int c;
		while (i < length)
		{
			if ((c = r.nextInt(90)) > 64 || (c = r.nextInt(122)) > 97)
			{
				sb.append((char) c);
				i++;
			}
		}
		return sb.toString();
	}
	
	
	public static boolean contains(String[] arr, String s)
	{
		if (arr != null)
		{
			for (int i = 0; i < arr.length; i++)
			{
				if (s.equals(arr[i]))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static String ljustZero(int i, int len)
	{
		return ljustZero(String.valueOf(i), len);
	}
	
	
	public static String ljustZero(String s, int len)
	{
		String id = "";
		for (int i = 0; i < len - s.length(); i++)
		{
			id += "0";
		}
		id += s;
		return id;
	}
	
	
	public static int getWordLength(String str)
	{
		int len = 0;
		if (StringUtil.isBlank(str))
		{
			return len;
		}
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++)
		{
			if (isChinese(ch[i]))
			{
				len = len + 2;
			}
			else
			{
				len = len + 1;
			}
		}
		return len;
	}
	
	
	public static String getWord(String str, int max)
	{
		char[] ch = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int len = 0;
		for (int i = 0; i < ch.length; i++)
		{
			if (isChinese(ch[i]))
			{
				len = len + 2;
			}
			else
			{
				len = len + 1;
			}
			if (len > max)
			{
				break;
			}
			sb.append(ch[i]);
		}
		return sb.toString();
	}
	
	
	public static boolean hasChinese(String s)
	{
		if (s == null)
			return true;
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (isChinese(chars[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean isChinese(char c)
	{
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
		{
			return true;
		}
		return false;
	}
	
	
	public static String[] splitIntoArr(String s)
	{
		if (isEmpty(s))
			return new String[] {};
		return s.split(",");
	}
	
	
	public static String join(String[] arr)
	{
		return join(arr, ",", "'");
	}
	
	
	public static String join(String[] arr, String separtor)
	{
		return join(arr, ",", "");
	}
	
	
	public static String join(String[] arr, String separtor, String quota)
	{
		String result = "";
		for (int i = 0; i < arr.length; i++)
		{
			result = result + quota + arr[i] + quota;
			if (i != arr.length - 1)
				result += separtor;
		}
		return result;
	}
	
	
	/**
	 * ת���ַ���룬��convertCode("someStr","iso-8859-1",UTF-8")
	 * 
	 * @param inStr
	 * @param encode1
	 *            ԭ�ַ����
	 * @param encode2
	 *            Ŀ���ִ�����
	 * @return
	 */
	public static String convertCode(String inStr, String encode1, String encode2)
	{
		if (isBlank(inStr))
			return EMPTY;
		try
		{
			return new String(inStr.getBytes(encode1), encode2);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return inStr;
		}
		
	}
	
	
	public static boolean isUpperCase(String s)
	{
		boolean result = true;
		char ch;
		for (int i = 0; i < s.length(); i++)
		{
			ch = s.charAt(i);
			if (!(ch >= 'A' && ch <= 'Z'))
			{
				result = false;
			}
		}
		return result;
	}
	
	
	public static boolean isLowerCase(String s)
	{
		boolean result = true;
		char ch;
		for (int i = 0; i < s.length(); i++)
		{
			ch = s.charAt(i);
			if (!(ch >= 'a' && ch <= 'z'))
			{
				result = false;
			}
		}
		return result;
	}
	
	
	public static int countBlanks(String s)
	{
		int count = 0;
		char[] chars = s.toCharArray();
		for (char ch : chars)
		{
			if (ch != CHAR_BLANK)
			{
				break;
			}
			count++;
		}
		return count;
	}
	
	
	public static String encodeIso(String sourceStr)
	{
		return encodeIso(sourceStr, "GBK");
	}
	
	
	public static String encodeIso(String sourceStr, String encoding)
	{
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(sourceStr))
		{
			try
			{
				result = new String(sourceStr.getBytes(encoding), "ISO8859_1");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
//	public static void main(String[] args)
//	{
//		String uid = "abc";
//		String encodeUid = StringUtil.base64Encode(uid);
//		System.out.println(encodeUid);
//		System.out.println(StringUtil.base64Decode(encodeUid));
//		try
//		{
//			System.out.println(encodePassword(uid, "MD5"));
//		}
//		catch (NoSuchAlgorithmException e)
//		{
//			e.printStackTrace();
//		}
//	}
}

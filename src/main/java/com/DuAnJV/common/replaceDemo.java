package com.DuAnJV.common;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.DuAnJV.dto.CartDTO;
import com.DuAnJV.dto.GioHangDTO;
import com.DuAnJV.models.Cart;
import com.DuAnJV.models.CartDetail;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.Giohang;
import com.DuAnJV.models.User;

public class replaceDemo {

	public static Map<String, String> mapFromFile = new HashMap<String, String>();
	public static Map<Integer, String> mapTrangThai = new HashMap<Integer, String>();
	public static List<Object[]> lsTrangThai = new ArrayList<Object[]>();
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
//		System.out.println("=>"+replace("sam sung"));
		//System.out.println("=>"+getBcrypt("12345"));
		//System.out.println("=>"+checkBcrypt("12345", "$2a$12$4vEjEBDa/LlHgnA1H93jC.Cx8nUK3Ob.PjX/jNN4fYPQ.SzWhq"));
	}

	public static void getHashMapFromTextFile(){

		if (0 == mapFromFile.size()) {
			try {
				Scanner in = new Scanner(new File("data.txt"));
				String line;
				while (in.hasNext())
				{
					line = in.nextLine();
					String[] keyvalue = line.split(":");
					mapFromFile.put(keyvalue[0].trim(), keyvalue[1].trim());
				} 
				in.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	public static String replace(String st) {
		getHashMapFromTextFile();
		if (null == st || st.equals("")) {
			return "";
		}else {
			st = st.toLowerCase().trim();
			String[] arr = st.split(" ");
			String search = "";
			String result = "";
			for (int i = 0; i < arr.length; i++) {
				String temp = (search + " " + arr[i]).trim();
				if (mapFromFile.containsKey(temp)) {
					search = temp;
				}else {
					search = search.replace(" ", "&").trim();
					if (!search.isEmpty()) {
						result = result + "(" + search + ")" + "|";
					}
					search = arr[i];
				}
			}
			search = search.replace(" ", "&").trim();
			result = result + "(" + search + ")";	
			return result;
		}
	}
	
	public static List<Long> tolistLong(List<Chucnang> lscn){
		List<Long> ls = new ArrayList<Long>();
		for (Chucnang cn : lscn) {
			ls.add(cn.getId());
		}
		return ls;
	}
	
	public static String getBcrypt(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
	
	public static boolean checkBcrypt(String password1,String password2){
		return BCrypt.checkpw(password1, password2);
	}
	
	public static String getMd5(String input) 
    { 
        try { 
        	input = "abc"+input+"xyz";
        	
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
	static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
    public static String toString(Date date,String...pa){
        if (pa.length > 0) {
            date_format.applyPattern(pa[0]);
        }
        if (null == date) {
			return null;
		}
        return date_format.format(date);
    }
    public static Date todate(String date,String...pa){
        try {
        	
            if (pa.length > 0) {
                date_format.applyPattern(pa[0]);
            }
            if (null == date || "".equals(date)) {
				return null;
			}else {
				date = date.replace("/", "-");
			}
            return date_format.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean checkQuyen(User user,String url) {
    	for (Chucnang chucnang : user.getChucnangs()) {
			if (chucnang.getUrl().equals(url)) {
				return true;
			}
		}
    	return false;
    }
    
    public static String convertFromListIntToString(List<Integer> ls) {
    	String str = ls.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static String convertFromListLongToString(List<Long> ls) {
    	String str = ls.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static String convertFromListByteToString(List<Byte> ls) {
    	String str = ls.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static void setmap() {
    	if (0 == mapTrangThai.size()) {
    		mapTrangThai.put(0, "Chưa thanh toán");
        	mapTrangThai.put(1, "Đang xử lý");
        	mapTrangThai.put(2, "Đã xử lý");
        	mapTrangThai.put(3, "Chờ giao hàng");
        	mapTrangThai.put(4, "Đã thanh toán");
		}
    }
    
    public static List<Object[]> convertMapToList(){
    	if (0 == lsTrangThai.size()) {
			for (int i = 0; i < mapTrangThai.size(); i++) {
				Object[] ob = new Object[2];
				ob[0] = i;
				ob[1] = mapTrangThai.get(i);
				lsTrangThai.add(ob);
			}
		}
    	return lsTrangThai;
    }	
    
    public static String getTrangThai(int trangthai) {
    	setmap();
    	return mapTrangThai.get(trangthai);
    }
    
    public static List<GioHangDTO> converttoDTO(List<Giohang> ls){
    	List<GioHangDTO> lsDto = new ArrayList<>();
    	for (Giohang gh : ls) {
			GioHangDTO ghdto = new GioHangDTO(gh.getId(), gh.getSoluong(), getTrangThai(gh.getTrangthai()), gh.getNgayadd(), gh.getProduct(), gh.getCustomer(),gh.getGiatien());
			lsDto.add(ghdto);
    	}
    	return lsDto;
    }
    
    public static double SUM(List<Giohang> ls) {
    	double sum = 0;
    	for (Giohang giohang : ls) {
			sum = sum + giohang.getSoluong() * giohang.getGiatien();
		}
    	return sum;
    }
    
    public static List<CartDTO> converCarttoDTO(List<Cart> ls){
    	List<CartDTO> lsdto = new ArrayList<>();
    	for (Cart cart : ls) {
			CartDTO cartdto = new CartDTO(cart.getId(), cart.getCustomer(), getTrangThai(cart.getTrangthai()),cart.getTongtien(),cart.getTrangthai());
			lsdto.add(cartdto);
		}
    	return lsdto;
    }
    
    public static String renderChitiet(List<CartDetail> ls,Cart cart) {
    	StringBuilder html = new StringBuilder();
    	html.append("	<table class=\"table table-bordered\"> ")
	    	.append("		<thead> ")
			.append("			<tr> ")
			.append("				<th>Người mua:</th> ")
			.append("				<td>"+cart.getCustomer().getFullname()+"</th> ")
			.append("				<th>Số tiền thanh toán:</th> ")
			.append("				<td>"+cart.getTongtien()+"</th> ")
			.append("			</tr> ")
			.append("		</thead> ")
    		.append("		<thead> ")
    		.append("			<tr> ")
    		.append("				<th>Hình ảnh</th> ")
    		.append("				<th>Tên sản phẩm</th> ")
    		.append("				<th>Số lượng</th> ")
    		.append("				<th>Giá bán</th> ")
    		.append("			</tr> ")
    		.append("		</thead> ")
    		.append("		<tbody> ");
    	for (CartDetail cartDetail : ls) {
			html.append("		<tr> ")
				.append("			<td width=\"100\"> ")
				.append("				<img alt=\"\" src=\"/getimage/"+cartDetail.getProduct().getImage()+"\" width=\"80\" height=\"70\"> ")
				.append("			</td> ")
				.append("			<td>"+cartDetail.getProduct().getTensanpham()+"</td> ")
				.append("			<td>"+cartDetail.getSoluong()+"</td> ")
				.append("			<td>"+cartDetail.getGiatien()+"</td> ")
				.append("		</tr> ");
		}
    	html.append("		</tbody> ")
    		.append("	</table> ");
    	return html.toString();
    }
    
}

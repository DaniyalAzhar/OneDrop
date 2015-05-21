package parse;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSONResult {

	JSONObject jsonObj;
	String jsonResult;
	public ParseJSONResult(String jsonResult){
		this.jsonResult=jsonResult;
		this.jsonObj=null;
	}
	public boolean getSuccess(){
		try {
			jsonObj=new JSONObject(jsonResult);
			int code=jsonObj.getInt("success");
			String result=jsonObj.getString("message");
			System.out.println(result);
			if(code==1){
				return true;

			}
			else{
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

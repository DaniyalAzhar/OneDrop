package parse;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseHTTPResponse {
	JSONObject jsonObj;
	String jsonResult;
	boolean responseOK=false;
	public ParseHTTPResponse(String jsonResult,boolean responseOK){
		this.jsonResult=jsonResult;
		this.responseOK=responseOK;
	}
	public boolean ParseJSONResult(){
		try {
			if(jsonResult.compareTo("")==0){
				return false;
			}
			jsonObj=new JSONObject(jsonResult);
			int code=jsonObj.getInt("success");
			String result=jsonObj.getString("message");
			System.out.println("JSONResponseMessage:\n"+result);
			if(code==1){
				return (true && responseOK);
			}
			else{
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

}

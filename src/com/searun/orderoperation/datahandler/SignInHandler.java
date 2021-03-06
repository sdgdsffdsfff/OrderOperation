package com.searun.orderoperation.datahandler;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 签到
 * 
 * @author zhazhaobao
 * 
 */
public class SignInHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<VehicleStatusDto> vehicleStatusDto;

	public SignInHandler(Context context,
			PdaRequest<VehicleStatusDto> vehicleStatusDto) {
		this.mContext = context;
		this.server_url = NetWork.SIGN_IN_ACTION;
		this.vehicleStatusDto = vehicleStatusDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		vehicleStatusDto.setUuId(CommonUtils.getUUID(mContext));
		vehicleStatusDto.setOriginApp("ANDROID");
		Log.i("canshu", new Gson().toJson(vehicleStatusDto).toString());
		httpAction.addBodyParam("jsonString", new Gson().toJson(vehicleStatusDto));
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SIGN_IN_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SIGN_IN_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SIGN_IN_ERROR, errorCode);
	}
}

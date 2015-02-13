package com.android.mina.charset;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.android.mina.domain.MsgPack;

public class MsgProtocolEncoder extends ProtocolEncoderAdapter{

	public MsgProtocolEncoder() {   
	}     
	//在此处实现对MsgProtocolEncoder包的编码工作，并把它写入输出流中     
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception { 
		if(message instanceof MsgPack){
			MsgPack mp = (MsgPack) message; 
			IoBuffer buf = IoBuffer.allocate(mp.getMsgLength());
			buf.order(ByteOrder.LITTLE_ENDIAN);
			buf.setAutoExpand(true);    
			//设置消息内容的长度
			buf.putInt(mp.getMsgLength()); 
			//设置消息的功能函数
			buf.putInt(mp.getMsgMethod());
			buf.putInt(mp.getMsgStatus());
			if (null != mp.getBytes()) {
				buf.put(mp.getBytes());
			}   
			buf.flip();     
			out.write(buf);  
			out.flush();
			buf.free();
		}
	}     
	public void dispose() throws Exception {     
	}


}

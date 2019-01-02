package com.zwdbj.server.shop_admin_service.mq;

import java.util.ArrayList;
import java.util.List;

public class MQWorkReceiverMgr {

    private List<MQWorkReceiver> receivers;

    public MQWorkReceiverMgr() {
        receivers = new ArrayList<>();
    }

    public void initReceivers(int num) {
        for (int i=1;i<=num;i++) {
            MQWorkReceiver receiver = new MQWorkReceiver();
            receivers.add(receiver);
            receiver.connect();
        }
    }

    private static volatile MQWorkReceiverMgr receiverMgr = null;
    public static MQWorkReceiverMgr shareReceiverMgr() {
        if (receiverMgr==null) {
            synchronized (MQWorkReceiverMgr.class) {
                if (receiverMgr == null) {
                    receiverMgr = new MQWorkReceiverMgr();
                }
            }
        }
        return receiverMgr;
    }
}

package com.a91coding.ruankao.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.a91coding.ruankao.model.QuestionItemBO;

public class QuestionBankService extends Service {
    public QuestionBankService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 根据ID 获得对应的题目
     * @param id
     * @return
     */
    public QuestionItemBO getQuestionItemById(int id) {
        return new QuestionItemBO();
    }

    public int nextQuestionItemId(int currentId, int step) {
        int id;
        id = currentId + step;
        if (id < 0) {
            id = 1;
        }
        return id;
    }
}

package com.vikendu.theservicesapp.utils;

import com.vikendu.theservicesapp.models.Advert;

import java.util.ArrayList;

public interface AdvertCallback {
    void onCallBack(ArrayList<Advert> adList);
}

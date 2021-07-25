package com.vikendu.theservicesapp.kotlin.activities.ui.providers.adcreation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.vikendu.theservicesapp.kotlin.repos.AdvertIdRepo;
import com.vikendu.theservicesapp.kotlin.repos.ServiceProviderRepo;
import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.models.ServiceProvider;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class AdCreationViewModel extends ViewModel {
    private final ServiceProviderRepo serviceProviderRepo;
    private final AdvertIdRepo advertIdRepo;

    private MutableLiveData<ServiceProvider> mutableProviderData;
    private MutableLiveData<String> mutableAdvertIdData;

    private DatabaseReference providerReference;
    private DatabaseReference advertReference;

    public AdCreationViewModel() {
        serviceProviderRepo = new ServiceProviderRepo();
        advertIdRepo = new AdvertIdRepo();

        providerReference = getFirebaseDatabase().getReference("providers");
        advertReference = getFirebaseDatabase().getReference("adverts");
    }

    public LiveData<ServiceProvider> getProviderData() {
        if(mutableProviderData == null) {
            mutableProviderData = serviceProviderRepo.getServiceProvider();
        }
        return mutableProviderData;
    }

    public LiveData<String> getAdId() {
        if(mutableAdvertIdData == null) {
            mutableAdvertIdData = advertIdRepo.getCurrentAdId();
        }
        return mutableAdvertIdData;
    }

    public void insertAd(Advert ad, int adCount, String adId, String uid) {
        if(adCount < 6) {
            providerReference.child(uid).child("ads").child(adId).setValue(ad);
            advertReference.child("notApproved").child(adId).setValue(ad);

            advertReference.child("adId").setValue(Integer.toString(Integer.parseInt(adId) + 1));
            providerReference.child(uid).child("adCount").setValue(adCount + 1)
                    .addOnSuccessListener(e -> Log.d("insert", "providerRef inserted"))
                    .addOnFailureListener(e -> Log.d("insert", "failed"));
        }
    }
}
package com.vikendu.theservicesapp.kotlin.activities.ui.providers.adcreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.models.ServiceProvider;
import com.vikendu.theservicesapp.utils.ActivityUtil;
import com.vikendu.theservicesapp.utils.FirebaseUtil;
import com.vikendu.theservicesapp.utils.ResourceUtil;

import org.jetbrains.annotations.NotNull;

import static com.vikendu.theservicesapp.utils.ActivityUtil.createNoActionSnackbar;
import static com.vikendu.theservicesapp.utils.ActivityUtil.hideKeyBoard;

public class AdCreationFragment extends Fragment {
    private AutoCompleteTextView mTagLine;
    private AutoCompleteTextView mAdDescription;
    private AutoCompleteTextView mPaisa;

    private TextView adTagLinePreview;
    private TextView adDescriptionPreview;
    private TextView adPricePreview;
    private TextView adStarRatingPreview;

    private Button previewButton;
    private Button submitButton;

    private AdCreationViewModel adViewModel;

    private ServiceProvider serviceProvider;

    private int adCount;
    private String rating;
    private String adId;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_ad, container, false);

        adViewModel = new AdCreationViewModel();

        mTagLine = rootView.findViewById(R.id.idAdCreationTaglineInput);
        mAdDescription = rootView.findViewById(R.id.idAdCreationDescInput);
        mPaisa = rootView.findViewById(R.id.idAdCreationPriceInput);

        adTagLinePreview = rootView.findViewById(R.id.idAdTagLine);
        adDescriptionPreview = rootView.findViewById(R.id.idAdDesc);
        adPricePreview = rootView.findViewById(R.id.idAdPrice);
        adStarRatingPreview = rootView.findViewById(R.id.idProviderRating);

        adViewModel.getAdId().observe(getViewLifecycleOwner(), s -> adId = s);
        adViewModel.getProviderData().observe(getViewLifecycleOwner(), provider -> serviceProvider = provider);

        previewButton = rootView.findViewById(R.id.idAdCreationPreviewBtn);
        submitButton = rootView.findViewById(R.id.idAdCreationSubmitBtn);

        previewButton.setOnClickListener(view -> showAdPreview(rootView));
        submitButton.setOnClickListener(view -> submitForApproval(rootView));
        return rootView;
    }

    private boolean areAnyFieldsEmpty() {
        return ResourceUtil.getString(mTagLine).equals("") || ResourceUtil.getString(mAdDescription).equals("") || ResourceUtil.getString(mPaisa).equals("");
    }

    private void createPreview() {
        ActivityUtil.hideKeyBoard(requireContext(), mPaisa);

        rating = serviceProvider.getRating();
        adCount = serviceProvider.getAdCount();

        if ( !ResourceUtil.getString(mTagLine).equals("") ) {
            adTagLinePreview.setText(ResourceUtil.getString(mTagLine));
        }
        if ( !ResourceUtil.getString(mAdDescription).equals("") ) {
            adDescriptionPreview.setText(ResourceUtil.getString(mAdDescription));
        }
        if ( !ResourceUtil.getString(mPaisa).equals("") ) {
            adPricePreview.setText("₹"+ResourceUtil.getString(mPaisa));
        }
        if ( !rating.equals("") ) {
            adStarRatingPreview.setText(rating);
        }
    }

    public void showAdPreview(View view) {
        if ( !areAnyFieldsEmpty() ) {
            hideKeyBoard(requireContext(), mPaisa);
            createPreview();
        } else {
            createNoActionSnackbar(view, "Please fill out all the fields").show();
        }
    }

    public void submitForApproval(View view) {
        if (!areAnyFieldsEmpty()) {
            hideKeyBoard(requireContext(), mPaisa);
            createPreview();

            String uid = FirebaseUtil.getUid();

            Advert ad = new Advert("default",
                    "default",
                    ResourceUtil.getString(mTagLine),
                    ResourceUtil.getString(mAdDescription),
                    ResourceUtil.getString(mPaisa),
                    adId,
                    uid,
                    serviceProvider.getRating(),
                    false,
                    false);

            adViewModel.insertAd(ad, adCount, adId, uid);
        } else {
            hideKeyBoard(requireContext(), mPaisa);
            createNoActionSnackbar(view, "Please fill out all the fields").show();
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
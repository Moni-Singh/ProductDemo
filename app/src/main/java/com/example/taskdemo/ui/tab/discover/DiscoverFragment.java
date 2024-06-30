package com.example.taskdemo.ui.tab.discover;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentDiscoverBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.productinterface.OnProductItemClickListener;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment implements OnProductItemClickListener {

    private DiscoverViewModel mViewModel;
    private FragmentDiscoverBinding binding;
    private Context mContext;
    private List<String> category = new ArrayList<>();
    private View progressLayout;
    private StickyHeaderItemDecoration stickyHeaderItemDecoration;
    private StickyHeaderAdapter stickyHeaderAdapter = new StickyHeaderAdapter(this);
    List<Product> products = new ArrayList<>();

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        progressLayout = binding.progressLayout.getRoot();
        mViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        setupRecyclerView();
        observeViewModel();

        // Show progress layout and make API calls
        progressLayout.setVisibility(View.VISIBLE);
        mViewModel.getProductByLimit();
        return root;
    }

    private void observeViewModel() {
        mViewModel.getProductCategoryLiveData().observe(getViewLifecycleOwner(), productCategory -> {
            progressLayout.setVisibility(View.GONE);
            if (productCategory != null) {
                category.clear();
                productCategory.forEach((k,v )->{
                    Product product = new Product();
                    product.setCategory(k);
                    product.setCategory(true);
                    products.add(product);
                    products.addAll(v);
                });
                stickyHeaderItemDecoration.setProductList(products);
                stickyHeaderAdapter.setProductList(products);
            } else {
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });

        mViewModel.getProductsByLimitLiveData().observe(getViewLifecycleOwner(), sliderProducts -> {
            if (sliderProducts != null) {
                Product product = new Product();
                product.setSlider(true);
                product.setProductList(sliderProducts);
                products.add(product);
                mViewModel.getProductsApi();

            } else {
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });
    }

    private void setupRecyclerView() {
        binding.displayProductRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.displayProductRv.setAdapter(stickyHeaderAdapter);
        stickyHeaderItemDecoration =new StickyHeaderItemDecoration(R.layout.item_header);
        binding.displayProductRv.addItemDecoration(stickyHeaderItemDecoration);
    }

    @Override
    public void onItemClick(Product Product) {
        if (Product != null ) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.PRODUCT_DETAILS, Product);
//            Navigation.findNavController(requireView()).navigate(R.id.navigation_productDetails, bundle);
        } else {
            HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
        }
    }
}

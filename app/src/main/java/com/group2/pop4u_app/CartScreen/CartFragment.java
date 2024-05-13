package com.group2.pop4u_app.CartScreen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.CartAdapter;
import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.SaleProductCardRecyclerAdapter;
import com.group2.api.Services.OrderService;
import com.group2.api.Services.ProductService;
import com.group2.database_helper.OrderContract;
import com.group2.database_helper.OrderDatabaseHelper;
import com.group2.model.Artist;
import com.group2.model.CartItem;
import com.group2.model.Product;
import com.group2.model.ResponseValidate;
import com.group2.pop4u_app.HomeScreen.FavoriteListActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.PaymentScreen.Payment;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentCartBinding;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    CartAdapter adapter;
    CompletableFuture<ArrayList<CartItem>> cartFuture;
    ArrayList<CartItem> carts, selectedCartItemList;
    BigProductCardRecyclerAdapter bigProductCardRecyclerAdapter;
    ArrayList<Product> productArrayList;
    CartItem undoCartItem;
    int undoPosition;
    OrderDatabaseHelper databaseHelper;
    Vibrator vibrator;

    Integer resetCheckAll = 0;
    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        initDbAndCartFuture();

        return binding.getRoot();
    }

    private void initDbAndCartFuture() {
        databaseHelper = new OrderDatabaseHelper(requireContext());
        carts = new ArrayList<>();
        cartFuture = OrderService.instance.getCart();
        cartFuture.thenAccept(cartItems -> {
            if (cartItems != null) {
                databaseHelper.clearAllData();
                carts.clear();
                carts.addAll(cartItems);
                for (CartItem item : carts) {
                    databaseHelper.insertDataWithCartItem(item);
                }
            }
            binding.rvCart.swapAdapter(adapter, true);
        });
    }

    private void loadRemoteDbAndSyncData () {
        if (networkIsConnected()) {
            try {
                cartFuture.get();
            } catch (Exception e) {
                Log.d("CartFragment", "onCreateView: " + e.getMessage());
            }
        } else {
            carts.clear();
            carts = databaseHelper.getAllData();
            binding.rvCart.swapAdapter(adapter, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRemoteDbAndSyncData();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customAndLoadData();
        loadRecommendProduct();
        addEvents();
        addItemClickEvents();

        binding.checkboxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (resetCheckAll == 0) {
                   adapter.selectAllItems(isChecked);
                } else {
                    if (resetCheckAll == 1) {
                        resetCheckAll = 0;
                        adapter.selectAllItems(isChecked);
                    } else if (resetCheckAll == 2 && !isChecked) {
                        resetCheckAll = 0;
                        adapter.selectAllItems(isChecked);
                    } else if (resetCheckAll == 3 && isChecked) {
                        resetCheckAll = 0;
                        adapter.selectAllItems(isChecked);
                    } else if (resetCheckAll == 4) {
                        resetCheckAll = 0;
                    }
                }
            }
        });

        adapter.setOnTotalPriceChangeListener(new CartAdapter.OnTotalPriceChangeListener() {
            @Override
            public void onTotalPriceChange(double totalPrice) {
                DecimalFormat df = new DecimalFormat("#,###");
                String formattedTotal = df.format(totalPrice);
                binding.totalPrice.setText(formattedTotal);
            }
            public void onAtLeastOneUnchecked() {
                resetCheckAll = 1;
                if (binding.checkboxSelectAll.isChecked()) {
                    resetCheckAll = 4;
                    binding.checkboxSelectAll.setChecked(false);
                }
            }

            public void allIsChecked(boolean isChecked) {
                if (isChecked) {
                    resetCheckAll = 2;
                } else {
                    resetCheckAll = 3;
                }
                binding.checkboxSelectAll.setChecked(isChecked);
                if (isChecked) {
                    selectedCartItemList = new ArrayList<>();
                    for (CartItem item : carts) {
                        if (item.isChecked()) {
                            selectedCartItemList.add(item);
                        }
                    }
                } else {
                    selectedCartItemList = new ArrayList<>();
                }
            }

        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    CartItem undoItem = carts.get(position);
                    deleteOrder(position);
                    vibrator = (Vibrator) requireContext().getSystemService(requireContext().VIBRATOR_SERVICE);
                    vibrator.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);
                    Snackbar.make(binding.ctnSnackBar, "Bạn đã xóa sản phẩm khỏi giỏ hàng.", Snackbar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            carts.add(position, undoItem);
                            adapter.notifyItemInserted(undoPosition);
                            databaseHelper.undoData(carts.get(position));
                        }
                    }).show();
                }
            }

            @Override
            public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
                return 0.75f;
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (dX < 0) {
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setTextSize(50);
                    paint.setTextAlign(Paint.Align.CENTER);
                    String text = "Xóa";
                    Rect bounds = new Rect();
                    paint.getTextBounds(text, 0, text.length(), bounds);
                    float x = viewHolder.itemView.getRight() + dX / 2;
                    float y = viewHolder.itemView.getTop() + viewHolder.itemView.getHeight() / 2 + bounds.height() / 2;

                    c.drawText(text, x, y, paint);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvCart);
    }
    private void customAndLoadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvCart.setLayoutManager(layoutManager);
        ItemOffsetVerticalRecycler itemOffsetVerticalRecycler = new ItemOffsetVerticalRecycler(requireContext(), R.dimen.small_offset);
        binding.rvCart.setHasFixedSize(true);
        binding.rvCart.addItemDecoration(itemOffsetVerticalRecycler);
        binding.rvCart.setNestedScrollingEnabled(false);
        adapter = new CartAdapter(getContext(), carts);
        binding.rvCart.setAdapter(adapter);
        adapter.setOnQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityDecrease(int position) {
                decreaseQuantity(position);
            }
            @Override
            public void onQuantityIncrease(int position) {
                increaseQuantity(position);
            }
        });
    }
    private void decreaseQuantity(int position) {
        CartItem item = carts.get(position);
        int currentQuantity = item.getQuantity();
        if (currentQuantity > 1) {
            currentQuantity--;
            item.setQuantity(currentQuantity);
            adapter.notifyItemChanged(position);
            databaseHelper.updateData(item.getProductCode(), currentQuantity);
        } else {
            // TODO: Xử lý khi số lượng sản phẩm bằng 1
            databaseHelper.deleteData(carts.get(position).getProductCode());
            carts.remove(position);
            binding.rvCart.swapAdapter(adapter, true);
        }
        calculateTotalPrice(item.isChecked());
        CompletableFuture<ResponseValidate> future = OrderService.instance.deteleUpdateItem(item.getProductCode(), 1);
        future.thenAccept(responseValidate -> {
            if (responseValidate != null) {
                if (responseValidate.getStatus() == 1) {
                    Log.d("CartFragment", "onQuantityDecrease: " + responseValidate.getMessage());
                } else {
                    Log.d("CartFragment", "onQuantityDecrease: " + responseValidate.getMessage());
                }
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("CartFragment", "onQuantityDecrease: " + e.getMessage());
        }
    }

    private void increaseQuantity(int position) {
        CartItem item = carts.get(position);
        int currentQuantity =item.getQuantity();
        currentQuantity++;
        item.setQuantity(currentQuantity);
        adapter.notifyItemChanged(position);
        databaseHelper.updateData(item.getProductCode(), currentQuantity);
        calculateTotalPrice(item.isChecked());
        CompletableFuture<ResponseValidate> future = OrderService.instance.addProductToCart(item.getProductCode(), 1);
        future.thenAccept(responseValidate -> {
            if (responseValidate != null) {
                if (responseValidate.getStatus() == 1) {
                    Log.d("CartFragment", "onQuantityDecrease: " + responseValidate.getMessage());
                } else {
                    Log.d("CartFragment", "onQuantityDecrease: " + responseValidate.getMessage());
                }
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("CartFragment", "onQuantityDecrease: " + e.getMessage());
        }
    }
    private void deleteOrder(int position) {
        databaseHelper.deleteData(carts.get(position).getProductCode());
        undoCartItem = carts.remove(position);
        adapter.notifyItemRemoved(position);
        calculateTotalPrice(undoCartItem.isChecked());
        CompletableFuture<ResponseValidate> future = OrderService.instance.deteleUpdateItem(undoCartItem.getProductCode(), undoCartItem.getQuantity());
        future.thenAccept(responseValidate -> {
            if (responseValidate != null) {
                if (responseValidate.getStatus() == 1) {
                    Log.d("CartFragment", "deleteOrder: " + responseValidate.getMessage());
                } else {
                    Log.d("CartFragment", "deleteOrder: " + responseValidate.getMessage());
                }
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("CartFragment", "deleteOrder: " + e.getMessage());
        }
    }
    private void calculateTotalPrice(Boolean isCurrentItemChecked) {
        if (isCurrentItemChecked) {
            double totalPrice = 0;
            for (CartItem item : carts) {
                if (item.isChecked()) {
                    totalPrice += item.getPrice() * item.getQuantity();
                }
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedTotalPrice = decimalFormat.format(totalPrice);

            // Hiển thị tổng thanh toán đã được định dạng trong TextView totalPrice
            binding.totalPrice.setText(formattedTotalPrice);
        }
    }

    private void calculateAllProductPrice(Boolean isCheckedAll) {
        double totalPrice = 0;
        if (isCheckedAll) {
            for (CartItem item : carts) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTotalPrice = decimalFormat.format(totalPrice);

        // Hiển thị tổng thanh toán đã được định dạng trong TextView totalPrice
        binding.totalPrice.setText(formattedTotalPrice);
    }

    private void loadRecommendProduct() {
        productArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcRelativeProduct.setLayoutManager(gridLayoutManager);
        binding.rcRelativeProduct.setHasFixedSize(true);


        productArrayList = new ArrayList<>();

        CompletableFuture<ArrayList<Product>> saleProductFuture = ProductService.instance.getListProduct(1, "sale", "desc", 10, 0, "", null, null, null);
        saleProductFuture.thenAccept(products -> {
            productArrayList.clear();
            productArrayList.addAll(products);
            bigProductCardRecyclerAdapter.notifyDataSetChanged();
        });

        try {
            saleProductFuture.get();
        } catch (Exception e) {
            Log.d("HomepageFragment", "loadData: " + e.getMessage());
        }


        bigProductCardRecyclerAdapter = new BigProductCardRecyclerAdapter(requireActivity(), productArrayList);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        binding.rcRelativeProduct.addItemDecoration(itemDecoration);
        binding.rcRelativeProduct.setAdapter(bigProductCardRecyclerAdapter);
        binding.rcRelativeProduct.setNestedScrollingEnabled(false);
    }
    private void addEvents(){
        binding.btnMuangay.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Payment.class);
            Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("selectedItems", (ArrayList<? extends Parcelable>) selectedCartItemList);
            if (selectedCartItemList == null) {
                selectedCartItemList = new ArrayList<>();
            }
            selectedCartItemList.clear();
            for (CartItem item : carts) {
                if (item.isChecked()) {
                    selectedCartItemList.add(item);
                }
            }
            bundle.putSerializable("listSelectedItem",(Serializable) selectedCartItemList);
            intent.putExtra("selectedItems", bundle);
            startActivity(intent);
        });
    }

    private boolean networkIsConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private void addItemClickEvents() {

        bigProductCardRecyclerAdapter.setOnClickListener(new BigProductCardRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                openProduct(product);
            }
        });

    }

    private void openProduct(Product product) {
        Intent intent = new Intent(requireActivity(), ProductDetailScreen.class);
        intent.putExtra("productCode", product.getProductCode());
        intent.putExtra("artistCode", product.getArtistCode());
        startActivity(intent);
    }
}
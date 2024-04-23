package com.group2.pop4u_app.CartScreen;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.CartAdapter;
import com.group2.database_helper.OrderDatabaseHelper;
import com.group2.model.CartItem;
import com.group2.model.Product;
import com.group2.pop4u_app.HomeScreen.FavoriteListActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.PaymentScreen.Payment;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentCartBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    CartAdapter adapter;
    ArrayList<CartItem> carts;
    BigProductCardRecyclerAdapter bigProductCardRecyclerAdapter;
    ArrayList<Product> productArrayList;
    CartItem undoCartItem;
    int undoPosition;
    OrderDatabaseHelper databaseHelper;

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
        if (getArguments() != null) {
        }
    }

//    private void loadDB() {
//        productArrayList = new ArrayList<>();
//        Cursor cursor = db.queryData("SELECT * FROM " + db.TABLE_NAME);
//        while (cursor.moveToNext()){
//            productArrayList.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
//        }
//        cursor.close();
//        adapter = new CartAdapter(CartFragment.this, R.layout.activity_item_cart, db);
//        binding.rvCart.setAdapter(adapter);
//    }

    private void createDB() {
        databaseHelper = new OrderDatabaseHelper(requireContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        createDB();
        return binding.getRoot();
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customAndLoadData();
        loadRecommendProduct();
        addEvents();

        binding.checkboxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Gọi phương thức selectAllItems của adapter để chọn hoặc bỏ chọn tất cả các mục hàng
                adapter.selectAllItems(isChecked);
                // Tính toán tổng thanh toán lại sau khi chọn hoặc bỏ chọn tất cả các mục hàng
                calculateTotalPrice();
            }
        });
        adapter.setOnTotalPriceChangeListener(new CartAdapter.OnTotalPriceChangeListener() {
            @Override
            public void onTotalPriceChange(double totalPrice) {
                // Cập nhật tổng giá tiền khi có sự thay đổi trong Adapter
                DecimalFormat df = new DecimalFormat("#,###");
                String formattedTotal = df.format(totalPrice);
                binding.totalPrice.setText(formattedTotal);
            }
            public void onAtLeastOneUnchecked() {
                // Đặt checkbox "Chọn tất cả" là false khi có ít nhất một checkbox cá nhân không được chọn
                binding.checkboxSelectAll.setChecked(false);
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

                    // Vẽ văn bản "Xóa" lên canvas
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


        carts = new ArrayList<>();
        try (Cursor cursor = databaseHelper.queryData("SELECT * FROM " + OrderDatabaseHelper.TABLE_NAME)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String code = cursor.getString(2);
                    int price = cursor.getInt(3);
                    int comparingPrice = cursor.getInt(4);
                    String image = cursor.getString(5);;
                    int quantity = cursor.getInt(6);

                    carts.add(new CartItem(code, image, name, price, comparingPrice, quantity, false));
                } while (cursor.moveToNext());
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new CartAdapter(getContext(), carts);
        binding.rvCart.setAdapter(adapter);
        adapter.setOnQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityDecrease(int position) {
                // Xử lý giảm số lượng sản phẩm
                decreaseQuantity(position);
            }
            @Override
            public void onQuantityIncrease(int position) {
                // Xử lý tăng số lượng sản phẩm
                increaseQuantity(position);
            }
        });
    }
    // Xử lý giảm số lượng sản phẩm
    private void decreaseQuantity(int position) {
        CartItem item = carts.get(position);
        int currentQuantity = item.getQuantity();
        if (currentQuantity > 1) {
            currentQuantity--;
            item.setQuantity(currentQuantity);
            adapter.notifyItemChanged(position);
            databaseHelper.updateData(item.getProductCode(), currentQuantity);
            calculateTotalPrice();
        }
    }
    private void increaseQuantity(int position) {
        CartItem item = carts.get(position);
        int currentQuantity =item.getQuantity();
        currentQuantity++;
        item.setQuantity(currentQuantity);
        adapter.notifyItemChanged(position);
        databaseHelper.updateData(item.getProductCode(), currentQuantity);
        calculateTotalPrice();
    }
    private void deleteOrder(int position) {
        databaseHelper.deleteData(carts.get(position).getProductCode());
        undoCartItem = carts.get(position);
        carts.remove(position);
        adapter.notifyItemRemoved(position);
        calculateTotalPrice();
    }
    private void calculateTotalPrice() {
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
    private void loadRecommendProduct() {
        productArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcRelativeProduct.setLayoutManager(gridLayoutManager);
        binding.rcRelativeProduct.setHasFixedSize(true);


        productArrayList = new ArrayList<>();

//        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));

        bigProductCardRecyclerAdapter = new BigProductCardRecyclerAdapter(requireActivity(), productArrayList);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        binding.rcRelativeProduct.addItemDecoration(itemDecoration);
        binding.rcRelativeProduct.setAdapter(bigProductCardRecyclerAdapter);
        binding.rcRelativeProduct.setNestedScrollingEnabled(false);
    }
    private void addEvents(){
        binding.btnMuangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Payment.class);
                startActivity(intent);
            }
        });
    }
}
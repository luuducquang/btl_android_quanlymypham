package com.example.btl_android_quanymypham.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.ChiTietHoaDonBanDAOAdmin;
import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.ChiTietHoaDonAdapterUser;
import com.example.btl_android_quanymypham.adapter.ChiTietHoaDonBanAdapterAdmin;
import com.example.btl_android_quanymypham.model.ChiTietHoaDonBanAdmin;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonFragmentUser extends DialogFragment {
    private RecyclerView recyclerView;
    private ChiTietHoaDonAdapterUser chiTietHoaDonAdapterUser;
    ChiTietHoaDonBanDAOAdmin db;
    TTMyPhamDAOAdmin ttMyPhamDAOAdmin;
    ImageView back;
    private int selectedId;
    private int MP_Value;
    private int DetailID;
    private  int index;
    public static ChiTietHoaDonFragmentUser newInstance(int detailID) {
        ChiTietHoaDonFragmentUser fragment = new ChiTietHoaDonFragmentUser();
        Bundle args = new Bundle();
        args.putInt("DetailID", detailID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogFragmentAttributes();
    }

    private void setDialogFragmentAttributes() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(layoutParams);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chitiethoadon_fragment_user,container,false);

        db = new ChiTietHoaDonBanDAOAdmin(requireContext());
        ttMyPhamDAOAdmin = new TTMyPhamDAOAdmin(requireContext());

        Bundle args = getArguments();
        if (args != null) {
            DetailID = args.getInt("DetailID", 0);
        }

        Innit(view);
        DataListView();
        HandlerButton();

        return view;
    }

    private void HandlerButton() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ChiTietHoaDonFragmentUser.this);
                fragmentTransaction.commit();
            }
        });

    }


    private void DataListView() {
        Cursor cursor = db.getAllChiTietHoaDonNhapByMaHDB(DetailID);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        List<ChiTietHoaDonBanAdmin> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int mahdb = cursor.getInt(1);
                int mamp = cursor.getInt(2);
                int soluong = cursor.getInt(3);
                Long dongia = cursor.getLong(4);
                Long tonggia = cursor.getLong(5);
                String tenmp = cursor.getString(6);
                byte[] hinhanh = cursor.getBlob(7);

                ChiTietHoaDonBanAdmin chiTietHoaDonBanAdmin = new ChiTietHoaDonBanAdmin(id, mahdb,mamp,soluong,dongia,tonggia,tenmp,hinhanh);
                data.add(chiTietHoaDonBanAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        chiTietHoaDonAdapterUser = new ChiTietHoaDonAdapterUser(data,requireContext());

        recyclerView.setAdapter(chiTietHoaDonAdapterUser);

    }


    private void Innit(View view) {
        back = view.findViewById(R.id.back);
        recyclerView = view.findViewById(R.id.rcv_chitiethoadon);
    }
}

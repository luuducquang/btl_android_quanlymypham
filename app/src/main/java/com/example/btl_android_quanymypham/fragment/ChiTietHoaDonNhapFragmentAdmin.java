package com.example.btl_android_quanymypham.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.ChiTietHoaDonNhapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.HoaDonNhapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.NhaCungCapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.ChiTietHoaDonNhapAdapterAdmin;
import com.example.btl_android_quanymypham.adapter.HoaDonNhapAdapterAdmin;
import com.example.btl_android_quanymypham.model.ChiTietHoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ChiTietHoaDonNhapFragmentAdmin extends DialogFragment {
    private RecyclerView recyclerView;
    private ChiTietHoaDonNhapAdapterAdmin chiTietHoaDonNhapAdapterAdmin;
    ChiTietHoaDonNhapDAOAdmin db;
    TTMyPhamDAOAdmin ttMyPhamDAOAdmin;
    EditText soluong,dongia,tongtien;
    ImageView back;
    Spinner mp;
    private int selectedId;
    Button them,lammoi,sua;
    private List<String> listMaMP;
    private List<String> listTenMP;
    private int MP_Value;
    private int DetailID;
    private  int index;
    public static ChiTietHoaDonNhapFragmentAdmin newInstance(int detailID) {
        ChiTietHoaDonNhapFragmentAdmin fragment = new ChiTietHoaDonNhapFragmentAdmin();
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
        View view = inflater.inflate(R.layout.chitiethoadonnhap_fragment_admin,container,false);

        db = new ChiTietHoaDonNhapDAOAdmin(requireContext());
        ttMyPhamDAOAdmin = new TTMyPhamDAOAdmin(requireContext());

        Bundle args = getArguments();
        if (args != null) {
            DetailID = args.getInt("DetailID", 0);
        }

        Innit(view);
        DataListView();
        HandlerButton();
        GetSpinnerMP();

        return view;
    }

    private void HandlerButton() {
        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong.setText("");
                dongia.setText("");
                tongtien.setText("");
                mp.requestFocus();
                selectedId = 0;
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSoLuong = soluong.getText().toString();
                String strDonGia = dongia.getText().toString();
                String strTongTien = tongtien.getText().toString();

                if (strSoLuong.isEmpty() || strDonGia.isEmpty() || strTongTien.isEmpty() ) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Integer SoLuong = Integer.valueOf(strSoLuong);
                        Long DonGia = Long.valueOf(strDonGia);
                        Long TongTien = Long.valueOf(strTongTien);
                        db.insertChiTietHoaDonNhap(DetailID, MP_Value, SoLuong, DonGia,TongTien);
                        DataListView();
                        soluong.setText("");
                        dongia.setText("");
                        tongtien.setText("");
                        mp.requestFocus();
                        Toast.makeText(requireContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSoLuong = soluong.getText().toString();
                String strDonGia = dongia.getText().toString();
                String strTongTien = tongtien.getText().toString();

                if (strSoLuong.isEmpty() || strDonGia.isEmpty() || strTongTien.isEmpty() ) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Integer SoLuong = Integer.valueOf(strSoLuong);
                        Long DonGia = Long.valueOf(strDonGia);
                        Long TongTien = Long.valueOf(strTongTien);
                        db.updateChiTietHoaDonNhap(selectedId,DetailID, MP_Value, SoLuong, DonGia,TongTien);
                        DataListView();
                        mp.requestFocus();
                        Toast.makeText(requireContext(), "Sửa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ChiTietHoaDonNhapFragmentAdmin.this);
                fragmentTransaction.commit();
            }
        });

    }


    private void GetSpinnerMP() {
        listMaMP = new ArrayList<>();
        listTenMP = new ArrayList<>();

        Cursor cursor3 = ttMyPhamDAOAdmin.getAllThongTinMyPham();
        if (cursor3 == null || cursor3.getCount() == 0) {
            return;
        }
        if (cursor3 != null && cursor3.moveToFirst()) {
            do {
                listMaMP.add(String.valueOf(cursor3.getInt(0)));
                listTenMP.add(cursor3.getString(1));
            } while (cursor3.moveToNext());
            cursor3.close();
        }
        ArrayAdapter adapterNCC = new ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,listTenMP
        );
        mp.setAdapter(adapterNCC);

        mp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MP_Value = Integer.parseInt(listMaMP.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void DataListView() {
        Cursor cursor = db.getAllChiTietHoaDonNhapByMaHDN(DetailID);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        List<ChiTietHoaDonNhapAdmin> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int mahdn = cursor.getInt(1);
                int mamp = cursor.getInt(2);
                int soluong = cursor.getInt(3);
                Long dongia = cursor.getLong(4);
                Long tonggia = cursor.getLong(5);
                String tenmp = cursor.getString(6);
                byte[] hinhanh = cursor.getBlob(7);

                ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin = new ChiTietHoaDonNhapAdmin(id, mahdn,mamp,soluong,dongia,tonggia,tenmp,hinhanh);
                data.add(chiTietHoaDonNhapAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        chiTietHoaDonNhapAdapterAdmin = new ChiTietHoaDonNhapAdapterAdmin(data,requireContext());

        recyclerView.setAdapter(chiTietHoaDonNhapAdapterAdmin);

        chiTietHoaDonNhapAdapterAdmin.setDelOnItemClickListener(new ChiTietHoaDonNhapAdapterAdmin.OnDelItemClickListener() {
            @Override
            public void onDelItemClick(ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin) {
                selectedId = chiTietHoaDonNhapAdmin.getId();
                DeleteItem();
            }
        });

        chiTietHoaDonNhapAdapterAdmin.setOnItemClickListener(new ChiTietHoaDonNhapAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin) {
                selectedId=chiTietHoaDonNhapAdmin.getId();
                soluong.setText(String.valueOf(chiTietHoaDonNhapAdmin.getSoLuong()));
                dongia.setText(String.valueOf(chiTietHoaDonNhapAdmin.getDonGia()));
                tongtien.setText(String.valueOf(chiTietHoaDonNhapAdmin.getTongTien()));
                index = findIndex(listTenMP, String.valueOf(chiTietHoaDonNhapAdmin.getTenMP()));
                mp.setSelection(index);
            }
        });
    }

    private static int findIndex(List<String> array, String targetValue) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(targetValue)) {
                return i;
            }
        }
        return -1;
    }

    private void DeleteItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa?");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteChiTietHoaDonNhap(selectedId);
                DataListView();
                Toast.makeText(requireContext(), "Đã xóa" , Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void Innit(View view) {
        mp = view.findViewById(R.id.spinnerMP);
        soluong = view.findViewById(R.id.txtSoLuong);
        dongia = view.findViewById(R.id.txtDonGia);
        tongtien = view.findViewById(R.id.txtTongTien);
        lammoi = view.findViewById(R.id.btnMoi);
        them = view.findViewById(R.id.btnThem);
        sua = view.findViewById(R.id.btnSua);
        back = view.findViewById(R.id.back);
        recyclerView = view.findViewById(R.id.rcv_chitiethoadonnhap);
    }
}

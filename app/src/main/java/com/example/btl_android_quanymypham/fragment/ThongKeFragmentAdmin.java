package com.example.btl_android_quanymypham.fragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.btl_android_quanymypham.DAO.ThongKeDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.HoaDonBanAdmin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ThongKeFragmentAdmin extends Fragment {
    EditText tungay,denngay;
    ImageView img_tungay,img_denngay;
    Button hdn,hdb,banchay,bancham,saphet;
    ListView lv;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Calendar calendar =Calendar.getInstance();

    ThongKeDAOAdmin db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_fragment_admin,container,false);

        db = new ThongKeDAOAdmin(requireContext());

        Innit(view);
        HandlerButton();

        return view;
    }

    private void HandlerButton() {
        img_tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int myear = year ;
                        int mmonth = month ;
                        int mdayOfMonth = dayOfMonth ;
                        GregorianCalendar c = new GregorianCalendar(myear,mmonth,mdayOfMonth);
                        tungay.setText(sdf.format(c.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                dialog1.show();
            }
        });
        img_denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int myear = year ;
                        int mmonth = month ;
                        int mdayOfMonth = dayOfMonth ;
                        GregorianCalendar c = new GregorianCalendar(myear,mmonth,mdayOfMonth);
                        denngay.setText(sdf.format(c.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                dialog1.show();
            }
        });

        hdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("Thống kê hoá đơn nhập");
                }
                String ngayBatDau = tungay.getText().toString();
                String ngayKetThuc = denngay.getText().toString();

                Cursor cursor = db.thongKeHoaDonNhap(ngayBatDau,ngayKetThuc);
                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> data = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(0);
                        int nguoitao = cursor.getInt(1);
                        String hoten = cursor.getString(2);
                        int mancc = cursor.getInt(3);
                        String tenncc = cursor.getString(4);
                        String ngaynhap = cursor.getString(5);
                        Long tongtien = cursor.getLong(6);
                        data.add(tenncc
                                +"\n"+"Ngày nhập: "+ngaynhap
                                +" | "+"Tổng tiền: "+tongtien + " đ"
                                +"\n"+"Người tạo: "+hoten);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                ArrayAdapter adapter = new ArrayAdapter(
                        requireContext(), android.R.layout.simple_list_item_1,data
                );
                lv.setAdapter(adapter);

            }
        });

        hdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("Thống kê hoá đơn bán");
                }
                String ngayBatDau = tungay.getText().toString();
                String ngayKetThuc = denngay.getText().toString();

                Cursor cursor = db.thongKeHoaDonBan(ngayBatDau,ngayKetThuc);
                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> data = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(0);
                        int nguoitao = cursor.getInt(1);
                        String tenkh = cursor.getString(2);
                        String diachi = cursor.getString(3);
                        String sdt = cursor.getString(4);
                        String ngayban = cursor.getString(5);
                        Long tongtien = cursor.getLong(6);
                        String hoten = cursor.getString(7);

                        data.add(tenkh
                                +" | "+"SDT: "+sdt
                                +"\n"+"Địa chỉ: "+diachi
                                +"\n"+"Ngày bán: "+ngayban
                                +" | "+"Tổng tiền: "+tongtien + " đ"
                                +"\n"+"Người tạo: "+hoten);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                ArrayAdapter adapter = new ArrayAdapter(
                        requireContext(), android.R.layout.simple_list_item_1,data
                );
                lv.setAdapter(adapter);

            }
        });

        banchay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("Thống kê sản phẩm bán chạy");
                }
                String ngayBatDau = tungay.getText().toString();
                String ngayKetThuc = denngay.getText().toString();

                Cursor cursor = db.thongKeSanPhamBanChay(ngayBatDau,ngayKetThuc);
                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> data = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String tenmp = cursor.getString(1);
                        String dungtich = cursor.getString(2);
                        String giaban = cursor.getString(5);
                        String soluong = cursor.getString(8);
                        Long daban = cursor.getLong(9);

                        data.add(tenmp
                                +"\n"+"Dung tích: "+dungtich
                                +" | "+"Đã bán: "+daban
                                +"\n"+"Số lượng còn: "+soluong
                                +" | "+"Giá bán: "+giaban+" đ");
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                ArrayAdapter adapter = new ArrayAdapter(
                        requireContext(), android.R.layout.simple_list_item_1,data
                );
                lv.setAdapter(adapter);

            }
        });

        bancham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("Thống kê sản phẩm bán chậm");
                }
                String ngayBatDau = tungay.getText().toString();
                String ngayKetThuc = denngay.getText().toString();

                Cursor cursor = db.thongKeSanPhamBanCham(ngayBatDau,ngayKetThuc);
                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> data = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String tenmp = cursor.getString(1);
                        String dungtich = cursor.getString(2);
                        String giaban = cursor.getString(5);
                        String soluong = cursor.getString(8);
                        Long daban = cursor.getLong(9);

                        data.add(tenmp
                                +"\n"+"Dung tích: "+dungtich
                                +" | "+"Đã bán: "+daban
                                +"\n"+"Số lượng còn: "+soluong
                                +" | "+"Giá bán: "+giaban+" đ");
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                ArrayAdapter adapter = new ArrayAdapter(
                        requireContext(), android.R.layout.simple_list_item_1,data
                );
                lv.setAdapter(adapter);

            }
        });

        saphet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("Thống kê sản phẩm sắp hết");
                }

                Cursor cursor = db.thongKeSanPhamSapHet();
                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> data = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String tenmp = cursor.getString(1);
                        String dungtich = cursor.getString(2);
                        String giaban = cursor.getString(5);
                        String soluong = cursor.getString(8);

                        data.add(tenmp
                                +"\n"+"Dung tích: "+dungtich
                                +"\n"+"Số lượng còn: "+soluong
                                +" | "+"Giá bán: "+giaban+" đ");
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                ArrayAdapter adapter = new ArrayAdapter(
                        requireContext(), android.R.layout.simple_list_item_1,data
                );
                lv.setAdapter(adapter);

            }
        });
    }

    private void Innit(View view) {
        tungay =view.findViewById(R.id.edBirthday);
        img_tungay =view.findViewById(R.id.imgdate);
        denngay =view.findViewById(R.id.edBirthday1);
        img_denngay =view.findViewById(R.id.imgdate1);
        hdn = view.findViewById(R.id.hoadonnhap);
        hdb = view.findViewById(R.id.hoadonban);
        banchay = view.findViewById(R.id.banchay);
        bancham = view.findViewById(R.id.bancham);
        saphet = view.findViewById(R.id.saphet);
        lv  = view.findViewById(R.id.listview_thongke);

        Calendar tungayCalendar = Calendar.getInstance();
        tungayCalendar.add(Calendar.DATE, -15);
        tungay.setText(sdf.format(tungayCalendar.getTime()));

        denngay.setText(sdf.format(calendar.getTime()));
    }
}

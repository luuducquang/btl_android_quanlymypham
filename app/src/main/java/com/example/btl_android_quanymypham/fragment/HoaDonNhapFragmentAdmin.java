package com.example.btl_android_quanymypham.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.HoaDonNhapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.LoaiMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.DAO.NhaCungCapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.HoaDonNhapAdapterAdmin;
import com.example.btl_android_quanymypham.adapter.LoaiMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.adapter.TTMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.LoaiMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HoaDonNhapFragmentAdmin extends Fragment {
    private RecyclerView recyclerView;
    private HoaDonNhapAdapterAdmin hoaDonNhapAdapterAdmin;
    HoaDonNhapDAOAdmin db;
    NhaCungCapDAOAdmin nhaCungCapDAOAdmin;
    TTMyPhamDAOAdmin ttMyPhamDAOAdmin;
    EditText soluong,dongia,tongtien,ngaynhap,search;
    ImageView chonngaynhap;
    Spinner ncc,mp;
    private int selectedId;
    private int DetailID;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Calendar calendar =Calendar.getInstance();
    Button them,lammoi;
    private List<String> listMaNCC;
    private List<String> listTenNCC;
    private int NCC_Value;
    private List<String> listMaMP;
    private List<String> listTenMP;
    private int MP_Value;
    TaiKhoanAdmin taiKhoanAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hoadonnhap_fragment_admin,container,false);

        db = new HoaDonNhapDAOAdmin(requireContext());
        nhaCungCapDAOAdmin = new NhaCungCapDAOAdmin(requireContext());
        ttMyPhamDAOAdmin = new TTMyPhamDAOAdmin(requireContext());


        Innit(view);
        DataListView();
        HandlerButton();
        GetIDUser();
        GetSpinnerNCC();
        GetSpinnerMP();

        return view;
    }

    private void GetIDUser() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
            if (taiKhoanAdmin != null) {
//                Toast.makeText(requireContext(), ""+taiKhoanAdmin.getId(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(requireContext(), "khong co du lieu", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(requireContext(), "khong co du lieu2", Toast.LENGTH_SHORT).show();
        }
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

    private void GetSpinnerNCC() {
        listMaNCC = new ArrayList<>();
        listTenNCC = new ArrayList<>();

        Cursor cursor2 = nhaCungCapDAOAdmin.getAllData();
        if (cursor2 == null || cursor2.getCount() == 0) {
            return;
        }
        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
                listMaNCC.add(String.valueOf(cursor2.getInt(0)));
                listTenNCC.add(cursor2.getString(1));
            } while (cursor2.moveToNext());
            cursor2.close();
        }
        ArrayAdapter adapterNCC = new ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,listTenNCC
        );
        ncc.setAdapter(adapterNCC);

        ncc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NCC_Value = Integer.parseInt(listMaNCC.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void DataListView() {
        Cursor cursor = db.getAllHoaDonNhap();
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        List<HoaDonNhapAdmin> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int nguoitao = cursor.getInt(1);
                String hoten = cursor.getString(2);
                int mancc = cursor.getInt(3);
                String tenncc = cursor.getString(4);
                String ngaynhap = cursor.getString(5);
                Long tongtien = cursor.getLong(6);

                HoaDonNhapAdmin hoaDonNhapAdmin = new HoaDonNhapAdmin(id, nguoitao,hoten,mancc,tenncc,ngaynhap,tongtien);
                data.add(hoaDonNhapAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        hoaDonNhapAdapterAdmin = new HoaDonNhapAdapterAdmin(data,requireContext());

        recyclerView.setAdapter(hoaDonNhapAdapterAdmin);

        hoaDonNhapAdapterAdmin.setDelOnItemClickListener(new HoaDonNhapAdapterAdmin.OnDelItemClickListener() {
            @Override
            public void onDelItemClick(HoaDonNhapAdmin hoaDonNhapAdmin) {
                selectedId = hoaDonNhapAdmin.getId();
                DeleteItem();
            }
        });

        hoaDonNhapAdapterAdmin.setInfOnItemClickListener(new HoaDonNhapAdapterAdmin.OnInfItemClickListener() {
            @Override
            public void onInfItemClick(HoaDonNhapAdmin hoaDonNhapAdmin) {
                DetailID = hoaDonNhapAdmin.getId();

                ChiTietHoaDonNhapFragmentAdmin chiTietDialog = ChiTietHoaDonNhapFragmentAdmin.newInstance(DetailID);
                chiTietDialog.show(getFragmentManager(), "chi_tiet_dialog");

            }
        });
    }

    private void DeleteItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa?");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteData(selectedId);
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

    private void HandlerButton() {

        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong.setText("");
                dongia.setText("");
                tongtien.setText("");
                soluong.requestFocus();
                selectedId = 0;
            }
        });
        chonngaynhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int myear = year ;
                        int mmonth = month ;
                        int mdayOfMonth = dayOfMonth ;
                        GregorianCalendar c = new GregorianCalendar(myear,mmonth,mdayOfMonth);
                        ngaynhap.setText(sdf.format(c.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                dialog1.show();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strNgayNhap = ngaynhap.getText().toString();
                String strSoLuong = soluong.getText().toString();
                String strDonGia = dongia.getText().toString();
                String strTongTien = tongtien.getText().toString();

                if (strNgayNhap.isEmpty() || strSoLuong.isEmpty() || strDonGia.isEmpty() || strTongTien.isEmpty() ) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else if(!(isValidFormat("dd/MM/yyyy",ngaynhap.getText().toString()))){
                    Toast.makeText(getContext(),"Không đúng định dạng ngày",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Integer SoLuong = Integer.valueOf(strSoLuong);
                        Long DonGia = Long.valueOf(strDonGia);
                        Long TongTien = Long.valueOf(strTongTien);
                        db.InsertData(taiKhoanAdmin.getId(), NCC_Value, strNgayNhap, TongTien, MP_Value, SoLuong, DonGia,TongTien);
                        DataListView();
                        soluong.setText("");
                        dongia.setText("");
                        tongtien.setText("");
                        ncc.requestFocus();
                        Toast.makeText(requireContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hoaDonNhapAdapterAdmin.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void Innit(View view) {
        ncc = view.findViewById(R.id.spinnerNCC);
        ngaynhap = view.findViewById(R.id.edBirthday);
        mp = view.findViewById(R.id.spinnerMP);
        soluong = view.findViewById(R.id.txtSoLuong);
        dongia = view.findViewById(R.id.txtDonGia);
        tongtien = view.findViewById(R.id.txtTongTien);
        lammoi = view.findViewById(R.id.btnMoi);
        them = view.findViewById(R.id.btnThem);
        recyclerView = view.findViewById(R.id.rcv_hoadonnhap);
        chonngaynhap = view.findViewById(R.id.imgdate);
        ngaynhap.setText(sdf.format(calendar.getTime()));
        search = view.findViewById(R.id.searchbar);
    }
    public boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
}

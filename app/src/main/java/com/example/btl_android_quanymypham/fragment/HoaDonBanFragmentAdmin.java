package com.example.btl_android_quanymypham.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.btl_android_quanymypham.DAO.HoaDonBanDAOAdmin;
import com.example.btl_android_quanymypham.DAO.HoaDonNhapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.NhaCungCapDAOAdmin;
import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.HoaDonBanAdapterAdmin;
import com.example.btl_android_quanymypham.adapter.HoaDonNhapAdapterAdmin;
import com.example.btl_android_quanymypham.model.HoaDonBanAdmin;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HoaDonBanFragmentAdmin extends Fragment {
    private RecyclerView recyclerView;
    private HoaDonBanAdapterAdmin hoaDonBanAdapterAdmin;
    HoaDonBanDAOAdmin db;
    TTMyPhamDAOAdmin ttMyPhamDAOAdmin;
    EditText tenkh,diachi,sdt,soluong,dongia,tongtien,ngayban,search;
    ImageView chonngayban;
    Spinner mp;
    private int selectedId;
    private int DetailID;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Calendar calendar =Calendar.getInstance();
    Button them,lammoi;
    private List<String> listMaMP;
    private List<String> listTenMP;
    private List<String> listgia;
    private Long Gia_Value;
    private int MP_Value;
    TaiKhoanAdmin taiKhoanAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hoadonban_fragment_admin,container,false);

        db = new HoaDonBanDAOAdmin(requireContext());
        ttMyPhamDAOAdmin = new TTMyPhamDAOAdmin(requireContext());


        Innit(view);
        DataListView();
        HandlerButton();
        GetIDUser();
        GetSpinnerMP();
        updatetotal();

        return view;
    }

    private void updatetotal() {
        soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String soluongString = soluong.getText().toString().trim();
                String dongiaString = dongia.getText().toString().trim();

                if (!soluongString.isEmpty() && !dongiaString.isEmpty()) {
                    int soluongValue = Integer.parseInt(soluongString);
                    int dongiaValue = Integer.parseInt(dongiaString);

                    tongtien.setText(String.valueOf(soluongValue * dongiaValue));
                }
                if (soluongString.isEmpty()){
                    tongtien.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        listgia = new ArrayList<>();

        Cursor cursor3 = ttMyPhamDAOAdmin.getAllThongTinMyPham();
        if (cursor3 == null || cursor3.getCount() == 0) {
            return;
        }
        if (cursor3 != null && cursor3.moveToFirst()) {
            do {
                listMaMP.add(String.valueOf(cursor3.getInt(0)));
                listTenMP.add(cursor3.getString(1));
                listgia.add(cursor3.getString(5));
            } while (cursor3.moveToNext());
            cursor3.close();
        }
        ArrayAdapter adapterMP = new ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,listTenMP
        );
        mp.setAdapter(adapterMP);

        mp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MP_Value = Integer.parseInt(listMaMP.get(position));
                Gia_Value = Long.parseLong(listgia.get(position));
                dongia.setText(""+Gia_Value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void DataListView() {
        Cursor cursor = db.getAllHoaDonBan();
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        List<HoaDonBanAdmin> data = new ArrayList<>();
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

                HoaDonBanAdmin hoaDonBanAdmin = new HoaDonBanAdmin(id, nguoitao,tenkh,diachi,sdt,ngayban,tongtien,hoten);
                data.add(hoaDonBanAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        hoaDonBanAdapterAdmin = new HoaDonBanAdapterAdmin(data,requireContext());

        recyclerView.setAdapter(hoaDonBanAdapterAdmin);

        hoaDonBanAdapterAdmin.setDelOnItemClickListener(new HoaDonBanAdapterAdmin.OnDelItemClickListener() {
            @Override
            public void onDelItemClick(HoaDonBanAdmin hoaDonBanAdmin) {
                selectedId = hoaDonBanAdmin.getId();
                DeleteItem();
            }
        });

        hoaDonBanAdapterAdmin.setInfOnItemClickListener(new HoaDonBanAdapterAdmin.OnInfItemClickListener() {
            @Override
            public void onInfItemClick(HoaDonBanAdmin hoaDonBanAdmin) {
                DetailID = hoaDonBanAdmin.getId();

                ChiTietHoaDonBanFragmentAdmin chiTietDialog = ChiTietHoaDonBanFragmentAdmin.newInstance(DetailID);
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
                tenkh.setText("");
                diachi.setText("");
                sdt.setText("");
                soluong.setText("");
                dongia.setText("");
                tongtien.setText("");
                tenkh.requestFocus();
                selectedId = 0;
            }
        });
        chonngayban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int myear = year ;
                        int mmonth = month ;
                        int mdayOfMonth = dayOfMonth ;
                        GregorianCalendar c = new GregorianCalendar(myear,mmonth,mdayOfMonth);
                        ngayban.setText(sdf.format(c.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                dialog1.show();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strTenKH = tenkh.getText().toString();
                String strDiaChi = diachi.getText().toString();
                String strSdt = sdt.getText().toString();
                String strNgayBan = ngayban.getText().toString();
                String strSoLuong = soluong.getText().toString();
                String strDonGia = dongia.getText().toString();
                String strTongTien = tongtien.getText().toString();

                if (strTenKH.isEmpty() ||strDiaChi.isEmpty() ||strSdt.isEmpty() ||strNgayBan.isEmpty() || strSoLuong.isEmpty() || strDonGia.isEmpty() || strTongTien.isEmpty() ) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else if(!(isValidFormat("dd/MM/yyyy",ngayban.getText().toString()))){
                    Toast.makeText(getContext(),"Không đúng định dạng ngày",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Integer SoLuong = Integer.valueOf(strSoLuong);
                        Long DonGia = Long.valueOf(strDonGia);
                        Long TongTien = Long.valueOf(strTongTien);
                        if (ttMyPhamDAOAdmin.kiemTraSoLuong(MP_Value,SoLuong)){
                            db.InsertData(taiKhoanAdmin.getId(), strTenKH, strDiaChi, strSdt,strNgayBan,TongTien, MP_Value, SoLuong, DonGia,TongTien);
                            DataListView();
                            tenkh.setText("");
                            diachi.setText("");
                            sdt.setText("");
                            soluong.setText("");
                            dongia.setText("");
                            tongtien.setText("");
                            tenkh.requestFocus();
                            Toast.makeText(requireContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                            db.updateDownSoluongThongTinMyPham(MP_Value,SoLuong);
                        }
                        else {
                            Toast.makeText(requireContext(), "Số lượng tồn kho không đủ", Toast.LENGTH_SHORT).show();
                        }

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
                hoaDonBanAdapterAdmin.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void Innit(View view) {
        tenkh = view.findViewById(R.id.txtTenKH);
        diachi = view.findViewById(R.id.txtDiaChi);
        sdt = view.findViewById(R.id.txtSDT);
        ngayban = view.findViewById(R.id.edBirthday);
        mp = view.findViewById(R.id.spinnerMP);
        soluong = view.findViewById(R.id.txtSoLuong);
        dongia = view.findViewById(R.id.txtDonGia);
        tongtien = view.findViewById(R.id.txtTongTien);
        lammoi = view.findViewById(R.id.btnMoi);
        them = view.findViewById(R.id.btnThem);
        recyclerView = view.findViewById(R.id.rcv_hoadonban);
        chonngayban = view.findViewById(R.id.imgdate);
        ngayban.setText(sdf.format(calendar.getTime()));
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

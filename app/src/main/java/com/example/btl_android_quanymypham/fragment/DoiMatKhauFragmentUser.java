package com.example.btl_android_quanymypham.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.GioHangDAOUser;
import com.example.btl_android_quanymypham.DAO.TaiKhoanDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.GioHangUser;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DoiMatKhauFragmentUser extends Fragment {

    TextInputLayout mk_cu,mk_moi;
    Button btn_doimk;
    TaiKhoanAdmin taiKhoanAdmin;
    TaiKhoanDAOAdmin taiKhoanDAOAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doimatkhau_fragment_user, container, false);
        taiKhoanDAOAdmin = new TaiKhoanDAOAdmin(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
        }

        Innit(view);
        HandlerButton();

        return view;
    }

    private void HandlerButton() {
        btn_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taiKhoanDAOAdmin.isLoginValid(taiKhoanAdmin.getId(),mk_cu.getEditText().getText().toString())){
                    taiKhoanDAOAdmin.changePassword(taiKhoanAdmin.getId(),mk_moi.getEditText().getText().toString());
                    Toast.makeText(requireContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    mk_cu.getEditText().setText("");
                    mk_moi.getEditText().setText("");
                }
                else {
                    Toast.makeText(requireContext(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Innit(View view) {
        mk_cu = view.findViewById(R.id.ed_mkcu);
        mk_moi = view.findViewById(R.id.ed_mkmoi);
        btn_doimk = view.findViewById(R.id.btn_doimk);
    }
}

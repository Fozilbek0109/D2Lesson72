package uz.coder.d2lesson72.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.coder.d2lesson72.databinding.ItemLayoutBinding;
import uz.coder.d2lesson72.models.Student;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    private List<Student>studentList;
    private onItemClick onItemClick;

    public MyAdapter(List<Student> studentList, MyAdapter.onItemClick onItemClick) {
        this.studentList = studentList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Student student = studentList.get(position);
        holder.binding.name.setText(student.getName());
        holder.binding.age.setText(String.valueOf(student.getAge()));
        holder.binding.phone.setText(student.getPhone());
        holder.binding.imgB.setOnClickListener(v -> onItemClick.onImgClickListener(student,position,holder.itemView));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        ItemLayoutBinding binding;
        public VH(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface onItemClick{
        void onImgClickListener(Student student,int position,View v);
    }
}

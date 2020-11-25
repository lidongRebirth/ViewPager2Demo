package com.myfittinglife.viewpager2demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_my.*


private const val ARG_PARAM1 = "param1"

/**
 @Author LD
 @Time 2020/11/25 16:13
 @Describe 几个界面通用该Fragment
 @Modify
*/
class MyFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        tvContent.text = param1
        when(param1){
            "1"->{
                ivPic.setImageResource(R.drawable.ic_pic1)
            }
            "2"->{
                ivPic.setImageResource(R.drawable.ic_pic2)
            }
            "3"->{
                ivPic.setImageResource(R.drawable.ic_pic3)
            }
            "4"->{
                ivPic.setImageResource(R.drawable.ic_pic4)
            }
        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
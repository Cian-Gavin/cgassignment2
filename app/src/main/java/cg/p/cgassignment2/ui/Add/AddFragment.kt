package cg.p.cgassignment2.ui.Add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cg.p.cgassignment2.databinding.FragmentAddgroupBinding
import cg.p.cgassignment2.models.ScoutGroupModels

class AddFragment : Fragment()
{
    private var _binding: FragmentAddgroupBinding? = null
    private lateinit var addViewModelObj: AddViewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {

        addViewModelObj = ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddgroupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.GroupName
        setButtonListener(binding)
        return root


    }
    fun setButtonListener(layout: FragmentAddgroupBinding) {
        layout.addButton.setOnClickListener {

            var name = ""
            if(layout.name.text.isNotEmpty())
                name = layout.name.text.toString()
            else
                Toast.makeText(context, "Please enter Scout Group Nmae", Toast.LENGTH_SHORT).show()

            var location =  ""
            if(layout.location.text.isNotEmpty())
                location =  layout.location.text.toString()
            else
                Toast.makeText(context,"Please enter Scout Group Location", Toast.LENGTH_LONG).show()
            if(name.isNotEmpty() && location.isNotEmpty())
                addViewModelObj.addgroup(ScoutGroupModels(name = name, location = location))

            Toast.makeText(context,"HERE" , Toast.LENGTH_LONG).show()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
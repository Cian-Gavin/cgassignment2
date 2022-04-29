package cg.p.cgassignment2.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cg.p.cgassignment2.adapters.AddClickListener
import cg.p.cgassignment2.adapters.AddgroupAdapter
import cg.p.cgassignment2.databinding.FragmentListBinding
import cg.p.cgassignment2.models.ScoutGroupModels
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment(), AddClickListener {

    private var _binding: FragmentListBinding? = null
    private lateinit var listViewModel: ListViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View {


        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableGroupList.observe(viewLifecycleOwner, Observer{
            groups ->
            groups?.let {render(groups) }
        })

        return root
    }

    private fun render(groupList: List<ScoutGroupModels>) {
        binding.recyclerView.adapter = AddgroupAdapter(groupList,this)
        if (groupList.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            //binding.donationsNotFound.visibility = View.VISIBLE
        } else {
             binding.recyclerView.visibility = View.VISIBLE
           // fragBinding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAddGroupClick(groupModels: ScoutGroupModels) {
        TODO("Not yet implemented")
    }


}
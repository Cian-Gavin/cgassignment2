package cg.p.cgassignment2.ui.List

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cg.p.cgassignment2.R
import cg.p.cgassignment2.adapters.AddClickListener
import cg.p.cgassignment2.adapters.AddgroupAdapter
import cg.p.cgassignment2.databinding.FragmentListBinding
import cg.p.cgassignment2.models.ScoutGroupModels
import cg.p.cgassignment2.ui.Authentication.LoggedInViewModel
import cg.p.cgassignment2.utils.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment(), AddClickListener {

    private var _binding: FragmentListBinding? = null
    private val _Fbinding get() = _binding!!
    lateinit var loader : AlertDialog
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root = _Fbinding.root
        loader = createLoader(requireActivity())

        _Fbinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        _Fbinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListGroupToAddGroup()
            findNavController().navigate(action)
        }
        showLoader(loader,"Downloading Scout Groups")
        listViewModel.observableGroupList.observe(viewLifecycleOwner, Observer{
            groups ->
            groups?.let {render(groups as ArrayList<ScoutGroupModels>)
                hideLoader(loader)
                checkSwipeRefresh()}
        })


        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Group")
                val adapter = _Fbinding.recyclerView.adapter as AddgroupAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listViewModel.delete(listViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as ScoutGroupModels).uid
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(_Fbinding.recyclerView)


        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onAddGroupClick(viewHolder.itemView.tag as ScoutGroupModels)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(_Fbinding.recyclerView)

        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_main_drawer, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(groupList: List<ScoutGroupModels>) {
        _Fbinding.recyclerView.adapter = AddgroupAdapter(groupList,this)
        if (groupList.isEmpty()) {
            _Fbinding.recyclerView.visibility = View.GONE
            _Fbinding.GroupsNotFound.visibility = View.VISIBLE

        } else {
            _Fbinding.recyclerView.visibility = View.VISIBLE
            _Fbinding.GroupsNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Groups")

        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listViewModel.liveFirebaseUser.value = firebaseUser
                listViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAddGroupClick(groupModels: ScoutGroupModels)
    {
        val action = ListFragmentDirections.actionListGroupToGroupInfo(groupModels.uid)
        findNavController().navigate(action)
    }
    private fun setSwipeRefresh() {
        _Fbinding.swiperefresh.setOnRefreshListener {
            _Fbinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Donations")
            listViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (_Fbinding.swiperefresh.isRefreshing)
            _Fbinding.swiperefresh.isRefreshing = false
    }
}
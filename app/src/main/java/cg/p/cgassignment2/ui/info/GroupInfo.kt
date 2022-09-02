package cg.p.cgassignment2.ui.info

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cg.p.cgassignment2.R
import cg.p.cgassignment2.databinding.FragmentGroupInfoBinding
import cg.p.cgassignment2.ui.Authentication.LoggedInViewModel
import cg.p.cgassignment2.ui.List.ListViewModel
import timber.log.Timber

class GroupInfo : Fragment() {


    private lateinit var groupInfoViewModel: GroupInfoViewModel
    private val args by navArgs<GroupInfoArgs>()
    private var _binding :FragmentGroupInfoBinding? = null
    private val _fbinding get() = _binding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val listViewModel : ListViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupInfoBinding.inflate(inflater, container, false)
        val root = _fbinding.root

        groupInfoViewModel = ViewModelProvider(this).get(GroupInfoViewModel::class.java)
        groupInfoViewModel.observableGroups.observe(viewLifecycleOwner, Observer { render() })

        _fbinding.editGroupButton.setOnClickListener {
            groupInfoViewModel.updateGroup(
                loggedInViewModel.liveFirebaseUser.value?.email!!,
                args.groupid, _fbinding.groups?.observableGroups!!.value!!
            )
            //Force Reload of list to guarantee refresh
            listViewModel.load()
            findNavController().navigateUp()
            //findNavController().popBackStack()
        }
        _fbinding.deleteGroupButton.setOnClickListener {
            listViewModel.delete(
                loggedInViewModel.liveFirebaseUser.value?.email!!,
                groupInfoViewModel.observableGroups.value?.uid!!)
            findNavController().navigateUp()
        }

        return root
        }

        private fun render() {
            _fbinding.groups = groupInfoViewModel
             //Timber.i("Retrofit _fbinding.groups == ${_fbinding.groups")
        }
    override fun onResume() {
        super.onResume()
        groupInfoViewModel.getGroup(loggedInViewModel.liveFirebaseUser.value?.uid!!,args.groupid)
    }


}
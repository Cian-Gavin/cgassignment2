package cg.p.cgassignment2.ui.Welcome

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cg.p.cgassignment2.databinding.FragmentWelcomeBinding
import cg.p.cgassignment2.utils.createLoader

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    lateinit var loader : AlertDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(WelcomeViewModel::class.java)

        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loader = createLoader(requireActivity())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
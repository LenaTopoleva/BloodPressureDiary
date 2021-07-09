package com.lenatopoleva.bloodpressurediary.ui.fragment

import android.annotation.SuppressLint
import android.icu.text.DateFormat.AM_PM_FIELD
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.lenatopoleva.bloodpressurediary.R
import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.utils.livedataevent.Event
import kotlinx.android.synthetic.main.dialog_fragment_enter_data.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.viewmodel.ext.android.getViewModel
import java.text.DateFormat.AM_PM_FIELD
import java.text.SimpleDateFormat
import java.util.*

class EnterDataDialogFragment : DialogFragment() {

    @ObsoleteCoroutinesApi
    private val model by lazy { requireParentFragment().getViewModel<DiaryViewModel>() }
    private val closeDialogObserver = Observer<Event<Int>> { closeDialog() }

    companion object {
        fun newInstance(healthData: HealthData?) =
            EnterDataDialogFragment().apply {
            arguments = Bundle().apply {
                healthData?.let {
                    putParcelable(HEALTH_DATA, healthData)
                }
            }
        }

        private const val HEALTH_DATA = "healthData"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setTitle("Enter your blood pressure and pulse")
        val v = View.inflate(context, R.layout.dialog_fragment_enter_data, null)
        return v
    }

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @ObsoleteCoroutinesApi
    private fun init() {
        btnYes.setOnClickListener{
            arguments?.getParcelable<HealthData>(HEALTH_DATA)?.let { healthData ->
                model.dialogFragmentBtnYesClicked(
                    healthData.copy(
                    upperBloodPressure = upper_blood_pressure_edit_text.text.toString(),
                    lowerBloodPressure = lower_blood_pressure_edit_text.text.toString(),
                    pulse = pulse_edit_text.text.toString()
                ))
            } ?: model.dialogFragmentBtnYesClicked(
                HealthData(
                    Calendar.getInstance().timeInMillis,
                    getCurrentDate(),
                    getCurrentTime(),
                    upper_blood_pressure_edit_text.text.toString(),
                    lower_blood_pressure_edit_text.text.toString(),
                    pulse_edit_text.text.toString()
                ))
            closeDialog()
        }
        btnCancel.setOnClickListener{
            model.dialogFragmentBtnCancelClicked()
            closeDialog()
        }

        arguments?.getParcelable<HealthData>(HEALTH_DATA)?.let { data ->
            upper_blood_pressure_edit_text.setText(data.upperBloodPressure)
            lower_blood_pressure_edit_text.setText(data.lowerBloodPressure)
            pulse_edit_text.setText(data.pulse)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM, yyyy")
//        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(date)
    }

    private fun closeDialog() {
        this.dismiss()
    }
}
package com.delet_dis.neumorphismcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {

    private var operation: Operation = Operation.EMPTY
    private var firstProcessingNumber = 0.0
    private var secondProcessingNumber = 0.0
    private var calculatorDisplayNonMock:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
        calculatorDisplayNonMock = findViewById(R.id.calculatorDisplayNonMock)
    }

    private fun equalsButtonOnclick() {
        try {
            secondProcessingNumber =
                calculatorDisplayNonMock!!.text.toString().replace(',', '.').toDouble()
            calculatorDisplayNonMock!!.text =
                if ((floor(calculateExpression()) == ceil(calculateExpression())))
                    calculateExpression()
                        .toString().replace(".0", "")
                else
                    calculateExpression().toString()
                        .replace('.', ',')
            operation = Operation.EMPTY
        } catch (e: NumberFormatException) {
            clearDisplay()
        }
    }

    private fun clearDisplay() {
        operation = Operation.EMPTY
        firstProcessingNumber = 0.0
        secondProcessingNumber = 0.0
    }


    private fun calculateExpression(): Double {
        return when (operation) {
            Operation.DIVIDE -> (firstProcessingNumber / secondProcessingNumber * 100000000).roundToLong()
                .toDouble() / 100000000
            Operation.MULTIPLY -> (firstProcessingNumber * secondProcessingNumber * 100000000).roundToLong()
                .toDouble() / 100000000
            Operation.MINUS -> ((firstProcessingNumber - secondProcessingNumber) * 100000000).roundToLong()
                .toDouble() / 100000000
            Operation.PLUS -> ((firstProcessingNumber + secondProcessingNumber) * 100000000).roundToLong()
                .toDouble() / 100000000
            Operation.PERCENT -> (firstProcessingNumber / 100 * secondProcessingNumber * 100000000).roundToLong()
                .toDouble() / 100000000
            else -> firstProcessingNumber
        }
    }

    private fun isAvailableToOperate(operation: Operation) {
        val calculatorDisplayNonMock = calculatorDisplayNonMock!!
        if (calculatorDisplayNonMock.text.toString()
                .isNotEmpty() && calculatorDisplayNonMock.text.toString() != "-"
        ) {
            onClickOperation(operation)
        }
    }

    private fun onAcButton() {}

    private fun onClickOperation(processingOperation: Operation) {
        val calculatorDisplayNonMock = calculatorDisplayNonMock!!
        if (operation == Operation.EMPTY) {
            if (calculatorDisplayNonMock.text.toString().isNotEmpty()) {
                firstProcessingNumber =
                    calculatorDisplayNonMock.text.toString().replace(',', '.').toDouble()
                calculatorDisplayNonMock.text = ""
                operation = processingOperation
            }
        }
    }

    private fun initListeners() {
        val group = findViewById<Group>(R.id.groupOfNumbers)
        val refIds = group.referencedIds
        for (id in refIds) {
            findViewById<View>(id).setOnClickListener {
                calculatorDisplayNonMock!!.text =
                    "${calculatorDisplayNonMock!!.text.toString()}${(it as? Button)?.text.toString()}"
            }
        }

        clearDisplay()
        val acButton = findViewById<Button>(R.id.acButton)
        val commaButton = findViewById<Button>(R.id.commaButton)
        val divideButton = findViewById<Button>(R.id.divideButton)
        val multiplyButton = findViewById<Button>(R.id.multiplyButton)
        val minusButton = findViewById<Button>(R.id.minusButton)
        val plusButton = findViewById<Button>(R.id.plusButton)
        val percentButton = findViewById<Button>(R.id.percentButton)
        val plusAndMinusButton = findViewById<Button>(R.id.plusAndMinusButton)
        val equalsButton = findViewById<Button>(R.id.equalsButton)
        val powerButton = findViewById<Button>(R.id.powerButton)

        acButton.setOnClickListener {
            clearDisplay()
        }

        commaButton.setOnClickListener {
            if (calculatorDisplayNonMock!!.text.toString()
                    .lastIndexOf(",") != calculatorDisplayNonMock!!.text.toString().length - 1 &&
                calculatorDisplayNonMock!!.text.split(',').size == 1
            )
                calculatorDisplayNonMock!!.text =
                    "${calculatorDisplayNonMock!!.text.toString()},"
        }

        divideButton.setOnClickListener {
            isAvailableToOperate(Operation.DIVIDE)
        }

        multiplyButton.setOnClickListener {
            isAvailableToOperate(Operation.MULTIPLY)
        }

        minusButton.setOnClickListener {
            val displayAsString = calculatorDisplayNonMock!!.text.toString()
            try {
                if (displayAsString.isNotEmpty()) {
                    onClickOperation(Operation.MINUS)
                }
            } catch (e: java.lang.NumberFormatException) {
                clearDisplay()
            }
        }

        plusButton.setOnClickListener {
            isAvailableToOperate(Operation.PLUS)
        }

        percentButton.setOnClickListener {
            isAvailableToOperate(Operation.PERCENT)
        }

        plusAndMinusButton.setOnClickListener {
            if (calculatorDisplayNonMock!!.text.toString()
                    .isNotEmpty() && calculatorDisplayNonMock!!.text.toString() != "-"
            ) {
                firstProcessingNumber =
                    +calculatorDisplayNonMock!!.text.toString().replace(',', '.').toDouble() * -1
                calculatorDisplayNonMock!!.text =
                    if ((floor(firstProcessingNumber) == ceil(firstProcessingNumber)))
                        firstProcessingNumber
                            .toString().replace(".0", "")
                    else
                        firstProcessingNumber.toString()
                            .replace('.', ',')
            }
        }

        equalsButton.setOnClickListener {
            equalsButtonOnclick()
        }

        powerButton.setOnClickListener {
            Toast.makeText(this, "Make me work!", Toast.LENGTH_LONG).show();
	    // This is called when the power button is pressed
            // Call the function that performs num1 by power of num2 calculation
	    // and show the result. explore the code for references on how this is
	    // done for other operations
        }
    }
}

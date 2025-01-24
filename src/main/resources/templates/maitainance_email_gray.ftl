<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Maintenance Charges Payment Confirmation</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #ffffff;
            color: #333333;
            margin: 0;
            padding: 0;
            line-height: 1.6;
        }
        .dark-mode {
            background-color: #333333;
            color: #ffffff;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f5f5f5; /* Slightly lighter background */
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Added drop shadow */
            border: 1px solid #999; /* Darkened border color */
        }
        .dark-mode .container {
            background-color: #444444;
            border: 1px solid #777; /* Darkened border color */
        }
        .header {
            font-size: 14px; /* Reduced font size */
            margin-bottom: 20px;
        }
        .content {
            font-size: 14px; /* Reduced font size */
        }
        .content p {
            margin: 10px 0;
        }
        .transaction-details {
            background-color: #f9f9f9; /* Slightly lighter background */
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            border: 1px solid #ddd; /* Darkened border color */
        }
        .dark-mode .transaction-details {
            background-color: #555555;
        }
        .transaction-details p {
            margin: 8px 0;
        }

        .signature {
            margin-top: 20px;
            font-size: 12px; /* Reduced font size */
            color: #777777;
        }
        .dark-mode .signature {
            color: #aaaaaa;
        }

        .footer {
            margin-top: 20px;
            font-size: 12px; /* Reduced font size */
            color: #777777;
            text-align: center;
        }
        .dark-mode .footer {
            color: #aaaaaa;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content">
            <div class="header">
                Hello,
            </div>
            <p>I have transferred Rs ${maintenanceCharge} towards maintenance charges for C3, Pacific Spring for the month of  ${paymentMonth} ${paymentYear}.</p>
            <p>Screenshot attached for your reference.</p>
            <div class="transaction-details">
                <p><strong>Paid money to:</strong> ${toAccountName}</p>
                <p><strong>Paid to:</strong> ${toBankAccount} </p>
                <p><strong>Amount:</strong> ₹${maintenanceCharge}</p>
                <p><strong>Status:</strong> Success</p>
                <p><strong>Remarks:</strong> ${message} </p>
                <p><strong>Bank Reference ID:</strong> ${utr}</p>
                <p><strong>Transaction Id:</strong> ${transactionId}</p>
            </div>
        </div>
        <div class="signature">
            Regards,<br>
            Rijas Rasheed
        </div>
        <div class="footer">
            &copy; ${.now?string('yyyy')} Rijas Rasheed. All rights reserved.
        </div>
    </div>
    <script>
        if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            document.body.classList.add('dark-mode');
        }
    </script>
</body>
</html>

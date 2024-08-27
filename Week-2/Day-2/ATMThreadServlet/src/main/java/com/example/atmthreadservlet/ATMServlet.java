package com.example.atmthreadservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ATMServlet")
public class ATMServlet extends HttpServlet {
    private int balance;
    private final Object lock = new Object();

    @Override
    public void init() throws ServletException {
        super.init();
        balance = 1000;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int amount = Integer.parseInt(request.getParameter("amount"));

        synchronized (lock) {
            switch (action) {
                case "deposit":
                {
                    threadSleep();
                    balance += amount;
                    System.out.println("deposit " + amount + " to " + balance);
                    break;
                }
                case "withdraw": {
                    if (balance >= amount) {
                        threadSleep();
                        balance -= amount;
                    } else {
                        response.getWriter().println("not enough");
                        System.out.println("not enough");
                    }
                    break;
                }
                default:  response.getWriter().println("invalid action");
            }
            response.getWriter().println("BALANCE: " + balance);
        }
    }


    private void threadSleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

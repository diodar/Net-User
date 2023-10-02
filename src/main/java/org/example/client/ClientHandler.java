package org.example.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.validation.Constants;

import java.util.Scanner;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final Scanner scanner = new Scanner(System.in);
    private ChannelHandlerContext ctx;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {

        this.ctx = ctx;

        if (!msg.equals(Constants.WRONG_USERNAME_PASS)) {
            System.out.println(Constants.SUCCESS_AUTH);
            System.out.println("SERVER: " + msg + '\n');
            System.out.println("Type '0' if you want to close the app.");

            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("0")) {
                System.out.println("Goodbye!");
                ctx.close();
            }
        } else {
            System.out.println("SERVER: " + msg + '\n');
            handleRetry();
        }
    }

    private void handleRetry() {
        System.out.println("Do you want to try again? (yes/no)");
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("yes")) {
            Channel channel = ctx.channel();
            channel.writeAndFlush(getData());
        } else {
            System.out.println("Goodbye!");
            ctx.close();
        }
    }

    public String getData() {

        String title = "Enter USER_NAME: ";
        System.out.print(title);
        String name = scanner.nextLine().toLowerCase();

        title = "Enter PASSWORD: ";
        System.out.print(title);
        String password = scanner.nextLine();

        return name + ";" + password;
    }
}

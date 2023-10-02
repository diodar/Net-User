package org.example.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.validation.Constants;
import org.example.validation.PasswordChecker;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    static final List<Channel> channels = new ArrayList<>();

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Client joined - " + ctx);
        channels.add(ctx.channel());
        System.out.println("Waiting for authorisation.");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {

        String[] parts = msg.split(";");
        if (parts.length == 2) {
            String receivedName = parts[0];
            String receivedPassword = parts[1];

            if (PasswordChecker.isPasswordExists(receivedName, receivedPassword)) {

                System.out.println("Authorised User: " + receivedName);
                for (Channel c : channels) {
                    c.writeAndFlush("Hello " + receivedName + '\n');
                }
            } else {
                for (Channel c : channels) {
                    c.writeAndFlush(Constants.WRONG_USERNAME_PASS);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Closing connection for client - " + ctx);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("Closing connection for client - " + ctx);
    }
}

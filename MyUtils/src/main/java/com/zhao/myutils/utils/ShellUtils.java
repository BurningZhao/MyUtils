package com.zhao.myutils.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Description:Shell命令执行工具
 */
public class ShellUtils {
    private static final String COMMAND_SU = "su";
    private static final String COMMAND_SH = "sh";
    private static final String COMMAND_EXIT = "exit\n";
    private static final String COMMAND_LINE_END = "\n";

    /**
     * 检查是否root
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    /**
     * 执行shell命令，默认返回结果信息
     *
     * @param command         命令
     * @param isRoot          是否需要root权限执行
     * @param isNeedResultMsg 是否需要返回结果信息
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * 执行shell命令
     *
     * @param commands        命令列表
     * @param isRoot          是否需要Root权限执行
     * @param isNeedResultMsg 是否需要返回执行结果
     */
    private static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            // get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }

    /**
     * 执行shell命令，默认返回结果信息
     *
     * @param command 命令
     * @param isRoot  是否需要root权限执行
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    /**
     * 执行shell命令，默认返回结果信息
     *
     * @param commands 命令列表
     * @param isRoot   是否需要root权限执行
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[commands.size()]), isRoot, true);
    }

    /**
     * 执行shell命令，默认返回结果信息
     *
     * @param commands 命令列表
     * @param isRoot   是否需要root权限执行
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     * 执行shell命令，默认返回结果信息
     *
     * @param commands        命令列表
     * @param isRoot          是否需要root权限执行
     * @param isNeedResultMsg 是否需要返回结果信息
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[commands.size()]), isRoot, isNeedResultMsg);
    }

    /**
     * 命令执行返回结果
     */
    public static class CommandResult {

        /**
         * result of command
         **/
        public final int result;
        /**
         * success message of command result
         **/
        public String successMsg;
        /**
         * error message of command result
         **/
        public String errorMsg;

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}

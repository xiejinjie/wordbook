package top.kcoder.wordbook.service;

import java.util.concurrent.Future;

/**
 * CmdService
 *
 * @author xiejinjie
 * @date 2022/12/22
 */
public interface IProcessService {
    /**
     * 执行命令，返回Process
     * @param cmd 命令
     * @return Process，提交失败返回null
     */
    Process exec(String[] cmd);

    /**
     * 执行命令并等待执行结束
     * @param cmd 命令
     * @return 处理结果
     */
    boolean execSync(String[] cmd);


    boolean execSync(String[] cmd, String directory);

    /**
     * 执行命令并等待执行结束
     * @param cmd 命令
     * @return 处理结果
     */
    Future<Boolean> execAsync(String[] cmd);

    String[] buildCmd(String cmd);
}

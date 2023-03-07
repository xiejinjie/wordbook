package top.kcoder.wordbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kcoder.wordbook.service.ICmdService;
import top.kcoder.wordbook.service.IProcessService;
import top.kcoder.wordbook.util.SystemUtil;

/**
 * CmdSericeImpl
 *
 * @author xiejinjie
 * @date 2023/2/28
 */
@Service
public class CmdServiceImpl implements ICmdService {

    @Autowired
    private IProcessService processService;

    @Override
    public void openBrowser(String url) {
        if (SystemUtil.isWindows()) {
            processService.execSync(processService.buildCmd("rundll32 url.dll,FileProtocolHandler " + url));
        }
        if (SystemUtil.isMacOs()) {
            processService.execSync(new String[]{"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", url});
        }
    }
}

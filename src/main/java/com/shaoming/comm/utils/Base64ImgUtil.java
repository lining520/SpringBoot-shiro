package com.shaoming.comm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 图片转Base64工具类
 */
public class Base64ImgUtil {
    private static final Logger log = LoggerFactory.getLogger(Base64ImgUtil.class);

    /**
     *  图片转化成base64字符串
     *  //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param path
     * @return
     */
    public static String GetImageStr(String path) {
        try {
            String imgFile = path;//待处理的图片
            InputStream in = null;
            byte[] data = null;
            //读取图片字节数组
            try {
                in = new FileInputStream(imgFile);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                log.info(e.getMessage(), e);
                e.printStackTrace();
            }
            //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);//返回Base64编码过的字节数组字符串
        }catch (Exception e){
            log.info(e.getMessage(),e);
            return null;
        }

    }

    /**
     *  //base64字符串转化成图片
     * @param imgStr base64 源串
     * @param imgPath 图片储存路径
     * @return
     */
     public static boolean GenerateImage(String imgStr,String imgPath) {
            System.out.print("已经收到了把字节码转化为图片的方法");
            //对字节数组字符串进行Base64解码并生成图片
            if (imgStr == null) //图像数据为空
                return false;

            BASE64Decoder decoder = new BASE64Decoder();
            try {    //Base64解码
                byte[] b = decoder.decodeBuffer(imgStr);
                for(int i=0;i<b.length;++i) {
                    if(b[i]<0) {//调整异常数据
                        b[i]+=256;
                    }
                }
                //生成jpeg图片
               // String imagePath="c:";
                //System.currentTimeMillis()
                //String imgFilePath = System.getProperty("user.dir")+ "/src/main/java/static/assets/headIcon/"+imgName+".jpg";//新生成的图片
                OutputStream out = new FileOutputStream(imgPath);
                out.write(b);
                out.flush();
                out.close();
                return true;
            } catch (Exception e) {
                log.info(e.getMessage(),e);
                return false;
            }
     }
    public static void main(String[] args) {
        String strImg = GetImageStr("D:\\源码\\Shiro-Demo-master\\src\\main\\java\\static\\assets\\headIcon\\404.png");
      //  String strImg="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAAAoCAIAAACTo5SwAAAYxElEQVR42u2biXfTdrbH569657133uvMtNNOpy3Q0oXpzDDtlOl0gTbsAQoB0pAQEhIgEAIhK1nIvjn75uxOHMfxvu+7ZcnabMmS5bybODgLTlgK7TtzqvM7PrKk2JY++t77vfen/GbleRZUNOe6LPKcGg+eUoUzQ3gG+eQgzlF0WZTpZTklnyASK78ur3j5zTMeJyACdt2OHw3jJwjqBkneD/qyptAqOW+Jwy4BXR1xW5w3xTkpx7SzkcooeZECovTNSGyeW+F+vdS/IEJuJdrK4sfI0Akb1eVLCStqdlkv3U8Iws7YV4BotJkhzlAgzdhk7NfL/QsgTLAJkBF5meY0jPncHR4jU7tgHbbEyciz3AQQV4nTVKQmuiK8vN/+a5B+FoTRRgb4QZCEddAc60VSu2J+1JJVtpsKty5xtwBaZAdfmhZZH0XrQ7FQNCEkfkW44wLSgWCYXPc3DATbx1O7kO5Jb23vc30Zr+WJC9TLEmI8wkVtOLHsJ5UBxk0KbPznvHBkDF/wTOhDip+fWSLKCiiZoJmnI0xEE+BHkhJMyY6QaiCEkjIdrIedPhtNUPyzehX4KPjAl2tTE5wAciTVQULuj1jCPMG+6itIc5TMO92lfyhxj+Es9nPCi3sQqqIXO1WGnSiFQZWLeLv/6SoEn5l6S6nMtrwqSIHw6lXoC7SLF5VzeRopGmOeSYWG+EtU4ba8yGEMbUQBJKVBYoFIIr7j1zDRBIELyRGhn+N+ivKRZf9cl+HhjHMYY5CfW3wsF86qpKr6ARuoMO5DYT18viI9QpaJzg/3JnMh2JltJQFYGEiBVRYNINQR6HWdrNysEhJbroWQiHMCE0/wG7+ASEBaZfpfrUriUT7qJEhFgFAEYAXeQrIO+OJLEra/I1JbRpYW4CV5W8adfLzqNtnVRC/MsF4XH+fTXRCeUQYWug31U44BJOL/RXKeQEZAeQKCb4pqJGxJgxAL+JpuXR1trVu97nQCdEOXRLZFP4SNgv6AH6wbSAzWfQy9fhETvI826dEZTUisRSc9lCGe4MDLUFdpGGBxf44bNi6AEEPLoanWUOWNVU41peRQd2RxjrWZeLeDx7F1FQIzh4WH7aN9UQAMR1bcImbHmZQ6Y3FWE5T1GBvE9t4A7Xmu3+Cft2Na30s8Lzy7lhmXb9xYkwrQZRqE0tH+ypyz8snRdSReAYp0Motk1JFEYj06jftd+VppSnkgx2GfY7V8EFhTeN4cltIcBiqM8LgjoPS0WfCTqzV+gn61/KIx9oOrp5Lj7ayT/3nkGIzfnzj2ztmj+y8d/yT3zMnK4rqJfjK6pRDieY7j1mMDQJ2fYoA3iFWrjuiQZZGxcczW7aWcz2fcIjHXiN7cLg8bAi/xBGMLOkiEQBH0B6/hc+VAMQ3C0ZY6QOg06jbHwFCxN3wU9523GO9MqTu7xD1tY4PddqnEo1oOmgx9ckmjYp6hCCu64CI1EEVXT8OyXtdj58OeAfMuyellLVKTNsnvzR9O/Me3x14/c+L93NW37+ee3Hf5+N4LGXvOZ+y7dOyrkiueoMfm1C8ppkYm2jp6K3TGpS11LM+PSJc7VI96tR1u0vbcdxJC2UQqa7cy4ideQvxM4HHBDa/ryptWgRYhfq4qcnRpRRDSIGwoygGEXGxL0opFaHzJh90N4icJGI7LDstNjbVyXlc9vFzdIq9sgqG836m9PWgtXPBmW9BMBMxnKMfnbzb7FVqNZsjpkEfxMM8yK4lXpcWW2VEAVjkkKrtOgJi2XFYm4vJauoa79l86/L+nD2bcOtsuetA/0iBZHDGaFfhaRkimcAum6zc3D1naxVLdnWthlnm+X4tbEEv7snNIx1E/KevHeYHjPAzfQcdu07FbMBi+LZ7wpPLiyuOKfAtCIPfk2B7i6QQtYcbv2/zXcfCr23rc4RN4OAdF7rh9D/VW0Zx2qFfZ3SZrrl+or5LUPWi6fbQkf//VnNfzrrxRmP92RemhgZYCm3TWq1EiFmPY46JRJEbTQvwFK7z8jlpAOCpXQkrDUBYJeQEPQAJUAAxG90BNfV/tWxe+Oph3Niizby4oE4mELWwcNLcOmFusYYOQEOC3ZH7Rd/X47cyDl079Nevs59llOZVi0TTP8emTn5AIypzmNrlv1irwTw85EMDnZzrrK8+VFHx2M//gncIv2hquSKa7nVafUmpVykcp9naEq+YFFagQXqNcDeDkBf1upf2zINzsZeThUIPFPKh1DeucjXL1jFMWZKN8Is2vj0TDZQ8/KyzZU3Rr3/UbewqL3r1W+Kf8a29dvfqHO8WfLLTUSBuqAXNqyJrrgD3cAabJUfvCrFuxFDDqMIeNDPgZAo/H0rR4gMHfii/86fLh4emBprbmdlEFMIMgOTrZvqScgrBJkBgck1hJAOaPC86kCkpc7jObVEOmtn5TsxlbzwIROnrm81sH/ivz6Mc/HD+wZRSeLolQ69mU5GksRsDgoqx73Aj8UI1vG9f0hQ1D11eeLc79NDkKsw/kXfgwO/P9iyf3FFz6cmFyjGJKqWhTYiW6qW5ik6IUEr7dujNRmgJszSXXdrl9oJwHhPAajfOuKK0hsEG/q96uqXcaqu1GGC1u65DfPY8G9STuY6Jw2OjEvcLSPY2is1jYnWxuchxjNM9WNxy5de8T2AsEIMZCpCX8PtRu9eu1rmWZTTJjFI9oBkSKzpbFpoebGUsba5Y7mpW9ncr+rqWBjllRU39rxW9P/u2NzM8Gh+oHR4ZqKubHhx0xdrtieCEOCA8Unku+9RD2EVOnSN+oVM3iGn+yoIQQVXimBfjln3zodwcTa5GfZWLyWWXOd4WHPzp1saCgWF9zXnnznKIYRvFChbJLYuiQjSsnnREvHM8SNOEKBDUW2o+mvYbDfeVFVz5tfpi/OLcoEWsmBhRjvdLupuabV/9VkP1JX8/hSKw8kaCeqH7ZCPcgytWvbCqutyO0apWAcKKr+VkQbt4i8U+YCBMrxAMsY6SIRQwZDXja3LaaNahZpR9nlewRuZRKHHVEKJyLJd0sSSGAsLTi4DMldp6P4JjLrNVIJ+ZHusRdtWMtlRPNldPNVfPNtQ/Liv5w7GBG9nGPctV2m3Tc/WICKoTpMQYLbZzwiGIBEJ6svuGjXOM2kcjYoEXknBBLFpReSXCyNVR9C//ra1lfv3dW2GrBAE/xQvUH7332t4++HgvMG0hbkEV9Zre5Y9nSp5i1LlYaWoDrLUX1kmIe0dspL8JHt2dELsb73Vh+1qeXTu0V98snBhXL82aXNRilV48kCaToyv6b+b/n4ulbd7yghbwYF+w7Ipzr7wKEFpX8eREqQ2IL6U5nqBJhLnb93oErt/cVNh5p1knqnWaAWmM3tXvsgBlgmygiCDd5uo453NEQADe7x7Th8bGXOVkz0s0/NmJMNAGmBigm68IRUXR8HDmQd/mPl77P6Sru0dcvuaROJ52qC+vur9aFD27g4y3Y0ffOZOw9XXKuzGv3r52F0O+dBDzNzn7AlvpxiMINwdM5pkcNLr/S5FcY7UbDI2MPSFPkEfObOhsRmnVag3KJGZiB5rIzP8g590HNvfM+j+2JNsJghKtc2bmJFeUeMrxoR4RdD0oAIYWHd0GYzIVQ0ae2GIggFPK+KLrTn/QOFd4o+zDv9ls3yz5q67lkdiu9TERHhiVocNDvbnZZkxG4yWXp97umfI55q3pONTM+2wMGJOlEUu4RCfni6TooSS8jMaq330Nr3Zmx8UDOndE/nD79P2e/2FvwTY1IXFrs36U7U5Fbm7E38/t3T2W8d/rW6bslwzXZ6lIz5di40GTEOaoDfo5RdUBpxKzuaAgXHjsdK+XK0ZQ12EUYSlr03oVJPWCDIRFrTRo3ipDdrUWpRNhS96Pbod2AHStn+b7dpmj4sUjsbnqEghCvunKu8Ubu7gEtwvOAUIZuFK3LmAcQ4hy1Y1+fCpXX/jOJECInjIbWU1aHLOXN/Ihn2SAblQx0D9UlmbX3VzWPtzbND3ZqF8c8NrBOVppEY2x8h5rkHyXZqbr+ybEn9+gbl//5v1l/P1T2g9XnfWqPNOhHzh66/PW+o4feOfzndw7tf/fzS9/kqaZVMTpKeoKBZYu1exniZ2DJyoTJbSUv1AOIH5cqtReX71RMdwA52azRbvJTxIY3IYnQ/VvfpCjCePgg02qSgX+BOAn+czcrK6jhmJTT2YIQynmQoLjj0VPT0hX1wrjflXo7izgBIRvfbfqXYtCuqasPHn6VRFhUuv/azffKq04Oj7WnDY8QgUmeA7ukJrCZkL/P52p0WpJibXXbhgPuBSxoIHH/ql2Kb+7LPAHv2BuXv3w799CJutwZ/dKO1yXOI3jQ6NbP62blukWJcqZjoq2stDzj72cg+X259/vv3jtx5N3jt0+XOKY0ls5lW6+KRemtjWXO4whBPTA5pARs8Doom7uguOXE0/dUuRizKOkpLzm8GWR3WzYeuREXLLuVjIINEKZ86RaEMvEQIDTIpU9FeM+k7HCZU2973FZVaILmdpt5CVG+eWOvSjff0lmQV7wvK/e3l37cdyl7/4M7FWqlbqfwuD2GCHE/GzVQ+AKGjGyyS7nTw69lf3+kvlRFYM4IDXYJiocwgzVKmz66cQQQ3u5vfCLACigZMnuMUr1kcLGvdfJR12z7pHJcbVN4A26/1xtG0QiKt2pFP1TnX/7ix+/2nMh4/9SRt4/XZFY6+jQRG5YsKEFboDDZjDEZKmdH1XqlE1QYXysNq60d3e6x3ZupWtVk7f1TSYTgZcZHv4JCcFeE7jVH406DcLChEhDioeBTL2W/115iWE69vWtULiMzKOPa3qog0CeL68m5XrVuoatVlJv3j4IbHxcUfDXVTb1wxwbiKsaxd8W9b+Ycvdj7qMfrqHOYa22qZtNAp+GhyNI3GzRfaK16N/dErbgPp8NWn1lmlA7LBtommzqmW8SKUYVF7go6aGY9C8RjXAQJoxaXz+X225031FWDVjFsXBxfyvz4/DevZ1z4S3bEQ7gXXeoJ09yIOkluYUoPOQ/H6MTWM5lBlgp0Fc9yIjr19P2b3xTlfniv5O0XVmGiOu98bX7Ws3yfhcIhHSYneyE1XlZJtJjCRWm3HaY1yC7m/e7ytdeXlJPLpkmjT5o8wzifGGokpJM2iKjFtz8dqCM81p/0jFvKyzB8VOGf7zbUDVt7J1yGAYunW+9uV9rfzDvz35eP3FyaaTQre2zqaY/FHEZofkP3UJtT/lDI4MjYd/LIe8fBntAh3B/w6Z0GLxXgKNYxpFusmjz8zolv956eHlatkhtUyMR607Q1tOzbaYbSz4TAnZL8Rsi9mX8Q1Jb2yZ8w6ivOPVCU97sXzIVhJAAS7K2592y910S+VjqDeGF9PuSD9TAbNGCzydbGxnXhWLAtwEmtHzVic0RsXd9eOwfYHJYg7Kp9lDHbT0+LqJ+CELzMe7nHhwxjXfq6bkNnt17do/OuDo2zV20QG/S3B9r+lHM089F9WTg0FvR2eOy1DhNE4Dq7udNhGbKYpg1Ghd7sdLrzj984/sm5+fHF1Xk3lpizLXo9HtuY1tSxPNo898WbGccPZGnkdr8Hgwov7Qzllpm7GAEI4TW1JdmUUSvEaZ4HoDDY9aB0zws6UqdRCwjb7xULawvLRHe/asM+R55GqiNQ4DfotUPuAYQYs31GTakZBE437n3UIc5BQrbk3aeaIxvLR2oaM2DXsqrProsBUd+86rmwsbzgIxmVDx3UmV7L+tcbP37Rpevo1ip6NaZRnUFmt3rDIeFxrcnx/J+v/wBK9WJIAs4Op8JOr01jUmqNEpN53GkXue0NawVrTn3HwfdPHPo4887dlp7hmUfjbc5Fk8vu6KzpP/ePnO/2n5nsm9lphpLSIoTcTxtRDmOSMntShQrZEHACLc5ONCNBZ/KCgCc3aOeqy47BrgVJ/gvWhSGfe3NrNM4/xVwwcR5MDYRTSISRtYMhF4LUtgkRlrGp8vw7b1+/uzfpRWFcufrhj7kfwsqwePVuwr00IDSMWZ7SFxYSoUjMEAhPW719eveazlzdupmikZuvZX1+uCLPjgS4nX92xXDXvh+PlbbUBdZqcIiZEDm3dU8oljO7gkWXq7546ygIDsbf//j1oTe/O7T3xJH3T379wakbxXXOKA1WeafczRNsxBIGkKQ6yPqo2YA8T3N/+3TeQOVmF7p5DInKkm7lBbszasnUw4JLUBr2VJU+08RmAhIrEXl81UCIVlyWmi983MEQIEdKzV0j4rvVDUdul/8FRn7+lzVVhR6vdq0RLHi6OkYaUNVs9ImW4ArBco5wROHDxdagKBkbYWgdA1rjiHFKZHg0ZG4vG20EedVN9KdP/ixHBzHU7NLPy/dd/H5/9vGAywv2ZPd6YLhrrjy/Pvvba599ePjTD77K/DK3KL9qbkqq87obHKtihTjc6bGPB71L4ZCFJkMxhtvUXQKzyrhJUhmoVDQ1arvjke2Z3uc1D/fdryjNADnCgNKir7PE7dQ91lnjWo80kq5HWp3qkcbiCZzlf7PyUpcnZ+3txLIRk8D65sNAc6bl9ds/NDPlam6UjVPgS9d+fdxLMpoAMeMI9Rl8SWawAm+1AQJ2UbGYNawfMLcMmtvsuBH8Udq+DBdhKB/YEzsILqAyhW3eKEaUD7bDkQ2TA+ua27UeSPVFIRLC61q444N+PxIMEizrjkY0RHgWDfT7XU2u9YK12WUd9LslaFBHhr1MxEb5zytvGnQ61kc/5zRvELIdw7Vum6mwhYcnHQPtetcDua9I4ro264TxkhEmn53x0kYdOvX42Rk9L8S2Ni8SgNCqXkVImYzmhzVePzI7RQ4+IoZM/iQzEBzIDsQHEgQhJh6r3IGbhixt/eZmC6ZLaT3Zl8EoAnDGyAjhDgS1ViAX1FgIp58l6NSMD0KE9189/WFe5rLcLBFrd68HNubA7aI7xgZhbRJttZpEkIDPx22d8IoJQpBlTMn+ftADdqnGbvpRU52nbejxOicRnwJH7REq/Li///QIJ5jo2MZ8oQHVPZDrAFjJgqNDj0jchBmLukj25atwlyfYNvmuOCBcVtIym39oyZRk1juGwMZFZ9iM0pDw4lun2QCei7ANWzv6TE1GVB0X+G1WggmTuMMXUJuBHKKzkV4kRkc3+/bk/AA4yVQ9sHl+4GkNxSjUdgAySRFI4xgGtT8T3c3xNTn7IQtqCL8SR6cQv8jnbFjv7xvb3LaRgEeKIUaKCLBRVojvcA19DN8GmW/R21k4Z69XqfUhN//EBOSrQrgdW4x3E1G1n5iyI31LAaDVOxvoVTsnVRZdkPRRjMO0akojZBob5qUcY7ZukbFRH1LwwkZSETg+GsKhgEvaE9TooAMoz2xBsm1+APhtrgeefQFjmWxbM/H1z6dIEkb6h5QErtnZn60uTYbfLWYyzvuYqJ7E59HgUMDd4l7v7zc6LX0+12wooCEwV5SObmpUMTxeJHEOWvw7/dvBK0QYZjgDQkqc6IDxcXjUeydtiFSLAy3tgNTT3ZH6l4yQj4eNqH/LlQ3QHrG9t8fYoAkuxR5fO56NASrU5ARsfqUJs7gjoXB808MQayqh084PCD/hvy+gtivW1+Rry2WrM/vCDroRFGH9dV0VjFAs/IyWEI2xVpqUh0PioLfL43joMC3jG3M+ECohfmLMjvfcK0QI/ADbqDmw5AlbURqLrqcB4AS0dHW9PL2R5JMbUwiRiB/ydrehXhlYYPlVKwTun/IiiD5pT8y43fvkFEE4RIEfmRldb3o9OT/wU58BFLgR/xzIC0C2u4blmC7IooAWaKlwY7d7DMjBXjgmJrx4symx1jXc+NJ4onDOOesifgGEkNBi6XpOSVrerc+YpBBiDDLjHO421M3bxHabyWt3wsACSMDiWLUnrgBL7ug7ABjUA8pFK1QIUCe8ovOCWCoOLFRbOyC0Jh+8gHFJdbvc0gLbU5H2JS7AD4S46CV/boQ7LdsEty47DwsbB2ZGO3W1LSMVD4pu3M++vm1UXb3VUV432TNkUmojVBqbznPxOC/8nOeCc1Ty8Sc+wb+irxB4jrCZpU4Mqohapd+OM//vEAIM6fj0o5J22NjW19laWyPuGlAvyM1qvdvqSKrQqjVoFxUz/aPdVY8AJOB8kFM81NTlNNsEQVj5913AK8hvXp069e1ScW4Ap0WmEATVu4ueATMq81GAE41yvwBCIrRaVAScqwgXxbNJJKLaadhIhp8p+gXcXtBibWEp/GHb/Vp4+++K0C0eBn72ge7pzCOOodW+KM3FoShs0QYB5Ksq7Z+6RGkBaLnMseGWHmDQU9Mc8gWh0oeNUPU/Rw+B50GsSZBwK/xbIgTxKe5cX+19PrgtvXoxTbXGxX8BhHxstTsz9GgZLr1keCIZCXWLzPAj8gU+jQwTA40d8FELo1P/ZvxYPAzi80yuTvr75mdAjgwWevKw/wOMzwCnDebqgwAAAABJRU5ErkJggg==";
        System.out.println(strImg);
       // GenerateImage(strImg, "D:/源码/Shiro-Demo-master/src/main/java/static/assets/images/5555.jpg");
    }
}


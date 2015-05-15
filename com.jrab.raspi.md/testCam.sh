#!/bin/bash
cd /home/pi/cam/picamTest

sudo raspistill -hf -vf -o image_default.jpg
sudo raspistill -t 1 -hf -vf -o image_t1.jpg
sudo raspistill -t 10000 -hf -vf -o image_t10000.jpg
sudo raspistill -q 100 -t 1 -hf -vf -o image_q100.jpg
sudo raspistill -sh -100 -n -t 1 -hf -vf -o image_sh-100.jpg
sudo raspistill -sh +100 -n -t 1 -hf -vf -o image_sh+100.jpg                                 
sudo raspistill -co +100 -n -t 1 -hf -vf -o image_co+100.jpg                                 
sudo raspistill -co -100 -n -t 1 -hf -vf -o image_co-100.jpg
sudo raspistill -br 0 -n -t 1 -o image_br_0.jpg                                      
sudo raspistill -br 100 -n -t 1 -hf -vf -o image_br_100.jpg
sudo raspistill -sa -100 -n -t 1 -hf -vf -o image_sa-100.jpg                                 
sudo raspistill -sa +100 -n -t 1 -hf -vf -o image_sa+100.jpg
sudo raspistill -ISO 100 -n -t 1 -hf -vf -o image_iso100.jpg                                 
sudo raspistill -ISO 200 -n -t 1 -hf -vf -o image_iso200.jpg
sudo raspistill -ISO 300 -n -t 1 -hf -vf -o image_iso300.jpg
sudo raspistill -ISO 400 -n -t 1 -hf -vf -o image_iso400.jpg
sudo raspistill -ISO 500 -n -t 1 -hf -vf -o image_iso500.jpg
sudo raspistill -ISO 600 -n -t 1 -hf -vf -o image_iso600.jpg
sudo raspistill -ISO 700 -n -t 1 -hf -vf -o image_iso700.jpg
sudo raspistill -ISO 800 -n -t 1 -hf -vf -o image_iso800.jpg
sudo raspistill -ev -10 -t 1 -hf -vf -o image_ev-10.jpg
sudo raspistill -ev 10 -t 1 -hf -vf -o image_ev10.jpg                                
sudo raspistill -ex auto -t 1 -hf -vf -o image_ex_auto.jpg
sudo raspistill -ex night -t 1 -hf -vf -o image_ex_night.jpg
sudo raspistill -ex backlight -t 1 -hf -vf -o image_ex_backlight.jpg
sudo raspistill -ex spotlight -t 1 -hf -vf -o image_ex_spotlight.jpg
sudo raspistill -ex sports -t 1 -hf -vf -o image_ex_sports.jpg
sudo raspistill -ex snow -t 1 -hf -vf -o image_ex_snow.jpg
sudo raspistill -ex beach -t 1 -hf -vf -o image_ex_beach.jpg
sudo raspistill -ex verylong -t 1 -hf -vf -o image_ex_verylong.jpg
sudo raspistill -ex antishake -t 1 -hf -vf -o image_ex_antishake.jpg
sudo raspistill -ex fireworks -t 1 -hf -vf -o image_ex_fireworks.jpg
sudo raspistill -awb off -t 1 -hf -vf -o image_awb_off.jpg
sudo raspistill -awb sun -t 1 -hf -vf -o image_awb_sun.jpg
sudo raspistill -awb tungsten -t 1 -hf -vf -o image_awb_tungsten.jpg
sudo raspistill -awb fluorescent -t 1 -hf -vf -o image_awb_fluorescent.jpg
sudo raspistill -awb incandescent -t 1 -hf -vf -o image_awb_incandescent.jpg
sudo raspistill -awb flash -t 1 -hf -vf -o image_awb_flash.jpg
sudo raspistill -awb horizon -t 1 -hf -vf -o image_awb_horizon.jpg
sudo raspistill -awb auto -t 1 -hf -vf -o image_awb_auto.jpg
sudo raspistill -ifx none -t 1 -hf -vf -o image_ifx_none.jpg
sudo raspistill -ifx negative -t 1 -hf -vf -o image_ifx_negative.jpg
sudo raspistill -ifx solarise -t 1 -hf -vf -o image_ifx_solarise.jpg
#sudo raspistill -ifx whiteboard -t 1 -hf -vf -o image_ifx_whiteboard.jpg
#sudo raspistill -ifx blackboard -t 1 -hf -vf -o image_ifx_blackboard.jpg
sudo raspistill -ifx sketch -t 1 -hf -vf -o image_ifx_sketch.jpg
sudo raspistill -ifx denoise -t 1 -hf -vf -o image_ifx_denoise.jpg
sudo raspistill -ifx emboss -t 1 -hf -vf -o image_ifx_emboss.jpg
sudo raspistill -ifx oilpaint -t 1 -hf -vf -o image_ifx_oilpaint.jpg
sudo raspistill -ifx hatch -t 1 -hf -vf -o image_ifx_hatch.jpg
sudo raspistill -ifx gpen -t 1 -hf -vf -o image_ifx_gpen.jpg
sudo raspistill -ifx pastel -t 1 -hf -vf -o image_ifx_pastel.jpg
sudo raspistill -ifx watercolour -t 1 -hf -vf -o image_ifx_watercolour.jpg
sudo raspistill -ifx film -t 1 -hf -vf -o image_ifx_film.jpg
sudo raspistill -ifx blur -t 1 -hf -vf -o image_ifx_blur.jpg
sudo raspistill -ifx saturation -t 1 -hf -vf -o image_ifx_saturation.jpg
sudo raspistill -ifx cartoon -t 1 -hf -vf -o image_ifx_cartoon.jpg
sudo raspistill -ifx colourswap -t 1 -hf -vf -o image_ifx_colourswap.jpg
sudo raspistill -ifx washedout -t 1 -hf -vf -o image_ifx_washedout.jpg
sudo raspistill -ifx posterise -t 1 -hf -vf -o image_ifx_posterise.jpg
sudo raspistill -ifx colourpoint -t 1 -hf -vf -o image_ifx_cartoon.jpg
sudo raspistill -ifx colourbalance -t 1 -hf -vf -o image_ifx_colourbalance.jpg
sudo raspistill -cfx 128:128 -t 1 -hf -vf -o image_cfx_128_128.jpg
sudo raspistill -cfx 255:255 -t 1 -hf -vf -o image_cfx_255_255.jpg
sudo raspistill -cfx 0:0 -t 1 -hf -vf -o image_cfx_0_0.jpg
sudo raspistill -cfx 0:255 -t 1 -hf -vf -o image_cfx_0_255.jpg
sudo raspistill -cfx 255:0 -t 1 -hf -vf -o image_cfx_255_0.jpg
sudo raspistill -mm average -hf -vf -o image_mm_average.jpg
sudo raspistill -mm spot -hf -vf -o image_mm_spot.jpg
sudo raspistill -mm backlit -hf -vf -o image_mm_backlit.jpg
sudo raspistill -mm matrix -hf -vf -o image_mm_matrix.jpg
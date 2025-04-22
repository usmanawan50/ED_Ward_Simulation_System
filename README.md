
# Emergency Department(ED) Ward Simulation Manager

GUI based Hospital Emergency Department(ED) ward simulation using Java 8.<br><br>
Emergency Department(ED) of a hospital is a specialized ward to deal semi-critical and severely critical cases. The ED ward consists of a finite number of beds and a waiting dorm for patients when all beds are occupied. Surgeons and internee surgeons treat the patients with specific ratios for doctors to internee doctors for each patient according to the severity. Each patient goes through the triage stage to get severity analysis and further operations.
Weibull distribution is used to calculate the time interval between patient arrivals and the time taken for patient treatment. The distribution has 2 independent variables ‘a = 1.5‘ and ‘b = 5’ and a user defined variable patient density to control patient flow.
The simulation logs are saved after each simulation ends and can be viewed later to investigate the effect of different factors on the optimization of ED ward facilities.



## Badges

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)


## Authors

- [@usmanawan50](https://github.com/usmanawan50/usmanawan50.git)


## Deployment(Desktop):

To deploy this project download the HEDv1.jar executable file and run it.

## FAQ

#### How are patients generated?

Patients are generated using Weibull's distribution. <br>In probability theory and statistics, the Weibull distribution /ˈwaɪbʊl/ is a continuous probability distribution. It models a broad range of random variables, largely in the nature of a time to failure or time between events. Examples are maximum one-day rainfalls and the time a user spends on a web page.(Wikipedia)

#### What does this project signify?

This application simulates patient flow in the Emergency Department(ED) which can be used to anticipate amount of resources required for the ED ward of a new 
hospital.


## Usage/Examples

![Demo GIF](output.gif)

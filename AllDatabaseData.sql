/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

INSERT INTO `accounts` (`id`, `owner_id`, `created`, `balance`, `account_number`) VALUES
(1, 1, '2023-05-27 12:20:43', 1300, '123456789012345');
INSERT INTO `accounts` (`id`, `owner_id`, `created`, `balance`, `account_number`) VALUES
(2, 1, '2023-05-29 16:44:48', 464, '98765432109876');
INSERT INTO `accounts` (`id`, `owner_id`, `created`, `balance`, `account_number`) VALUES
(3, 3, '2023-05-30 10:52:46', 40000, '775647467160903');
INSERT INTO `accounts` (`id`, `owner_id`, `created`, `balance`, `account_number`) VALUES
(6, 8, '2023-05-30 10:54:56', 8347474, '444474016271738'),
(7, 5, '2023-05-30 10:54:56', 934020, '609833083471569'),
(8, 4, '2023-05-30 10:54:56', 3984028, '140728918186647'),
(9, 5, '2023-05-30 11:00:19', 93874092, '295611740767964'),
(10, 6, '2023-05-30 11:00:19', 904580497, '427235942573258'),
(11, 7, '2023-05-30 11:00:42', 392000, '589336273708825'),
(12, 9, '2023-05-30 11:02:52', 9, '690234437186410'),
(13, 12, '2023-05-30 11:03:24', 1211, '728090566868256'),
(14, 13, '2023-05-30 11:03:57', 1183, '763723323694107'),
(15, 20, '2023-05-30 11:04:23', 297555, '328752227636511'),
(16, 17, '2023-05-30 11:04:48', 393274, '858517275373392'),
(17, 15, '2023-05-30 11:05:07', 239085, '473052955733964'),
(18, 16, '2023-05-30 11:05:30', 135, '498067090069227'),
(19, 18, '2023-05-30 11:05:57', 9742, '708867631959498'),
(20, 19, '2023-05-30 11:06:22', 1069, '884722259767815'),
(21, 2, '2023-05-30 11:06:56', 14, '947752039397431'),
(23, 18, '2023-06-01 11:53:34', 42069, '178615485818511'),
(24, 22, '2023-06-02 13:09:34', 69420, '226748131268940'),
(25, 22, '2023-06-02 13:11:11', 600, '811218505744097'),
(26, 22, '2023-06-02 13:11:34', 666, '148066996375284');

INSERT INTO `transactions` (`id`, `receiver_account_id`, `sender_account_id`, `amount`, `date_sent`) VALUES
(1, 3, 1, 200, '2023-05-21 04:30:40');
INSERT INTO `transactions` (`id`, `receiver_account_id`, `sender_account_id`, `amount`, `date_sent`) VALUES
(2, 9, 2, 6, '2023-05-21 05:51:51');
INSERT INTO `transactions` (`id`, `receiver_account_id`, `sender_account_id`, `amount`, `date_sent`) VALUES
(3, 8, 2, 200, '2023-05-22 09:30:19');
INSERT INTO `transactions` (`id`, `receiver_account_id`, `sender_account_id`, `amount`, `date_sent`) VALUES
(4, 3, 2, 200, '2023-05-26 12:21:03'),
(5, 21, 2, 1, '2023-05-27 01:27:50'),
(6, 13, 2, 5, '2023-05-29 14:41:09'),
(7, 11, 2, 50, '2023-05-30 18:41:58'),
(8, 18, 2, 46, '2023-05-31 08:08:08'),
(9, 20, 1, 1000, '2023-05-31 19:56:51'),
(10, 13, 1, 300, '2023-06-01 09:33:41'),
(11, 19, 3, 200, '2023-06-01 09:41:19'),
(14, 14, 11, 758, '2023-06-01 14:51:30'),
(15, 16, 25, 400, '2023-06-02 13:30:48');

INSERT INTO `users` (`id`, `personnummer`, `email`, `phone`, `password`, `address`, `name`, `created`) VALUES
(1, '200001010001', 'maukus@gmail.com', '1234567890', '1f8d1a1de98932d37ad5703ef64dc98ccdeea5df42f114f594ca43f11b5c6c32bb1b8efff9a17c2943f4607ba240c547', 'Handslagarevägen 86', 'Maukus Hansson', '2023-05-27 14:15:41');
INSERT INTO `users` (`id`, `personnummer`, `email`, `phone`, `password`, `address`, `name`, `created`) VALUES
(2, '196904206969', 'kalleanka@gmail.com', '4206969692', '6d330c3b8ec295eed2519e3562e0916956e34e8545b6537fd87ba7d0e155c343b1284ebdfbf971b847cf2cebe25848eb', 'Kallesgata 69', 'Kalle Anka', '2023-05-29 13:01:44');
INSERT INTO `users` (`id`, `personnummer`, `email`, `phone`, `password`, `address`, `name`, `created`) VALUES
(3, '199406040356', 'MaxFlink@gmail.com', '4068804262', '9a1f1bd976072548307533f43c1c8d63358ee70fd2f2eb9845964cab5f6dac1bf38e2d0a32ea11f07df3bfc4a3c392a0', 'Flinkgatan 19', 'Max Flink', '2023-05-30 10:11:10');
INSERT INTO `users` (`id`, `personnummer`, `email`, `phone`, `password`, `address`, `name`, `created`) VALUES
(4, '197811036446', 'TaraLindberg@rhyta.com', '01409641018', 'dc54720e53fe129d117aed0fc9f56c41b5505acbed48e4d37a180ae6b474d83dfb4661cf5696d194aa96ce105c51586c', 'Tjörneröd 97', 'Tara Lindberg', '2023-05-30 10:16:03'),
(5, '199401234530', 'EdwinSandberg@rhyta.com', '09528671056', '493aa5195cb6aea7dc6e03b78a1005705a1f951b2cfff743fd23cd7e41bb490dcbeccc2c7b92be3c88bab8d5d787fdcd', 'Liljerum Grenadjärtorpet 14', 'Edwin Sandberg', '2023-05-30 10:17:31'),
(6, '200104077805', 'AlisaForsberg@rhyta.com', '06965898899', 'cfa5b3a272dbdecec9a726e250ab879c5dd724d5d8cb5b4218f4867ec209592c2248df899e0a75c9bedcd52eddb23a11', 'Pite Långvik 46', 'Alisa Forsberg', '2023-05-30 10:20:04'),
(7, '195307060672', 'EdwinNorberg@teleworm.us', '03718805322', '141ade92b0698a05afee3574a73eea14d0447422f37ad08c96d82452f1a84b9321e1e3ef5792e3fb01f59ac972b8c819', 'Skällebo 89', 'Edwin Norberg', '2023-05-30 10:21:54'),
(9, '194204199089', 'BeatriceLindstrom@armyspy.com', '194204199089', '03c04de787b224ecac731319c7a91c49ad67718d8a6c6f326a8f6a9909989b5b21a1ca69ff09caf778775b4c5c630792', 'Nöjesgatan 86', 'Beatrice Lindström', '2023-05-30 10:25:14'),
(12, '200111063947', 'VanesaNilsson@rhyta.com', '04576725277', '5fcbc4047fcfcf6ac79c549cdd385910d87290d6c8c0a4eddb728aa91f23cd6c302dbb30115cb5c0189c6aacd719e8f9', 'Nittsjö Kvarngatu 51', 'Vanesa Nilsson', '2023-05-30 10:29:15'),
(13, '196701154723', 'MatildaBerg@armyspy.com', '09237323504', 'e487b7e37d1bb7e7fa71c8984e4bec0c3c24728858d84ccd32105d303a7fd8e5cfa37a373eb76eb146069631018cf5dc', 'Västerviksgatan 12', 'Matilda Berg', '2023-05-30 10:30:39'),
(14, '195703144443', 'TanjaHermansson@rhyta.com', '04183947964', 'ff9a38b49e9661cddff8edd865b26cf4a9e5f38b3767225906f5d060ffdcea77f1aa37ee19c400c0d485f2da608d5029', 'Mogata Sjöhagen 93', 'Tanja Hermansson', '2023-05-30 10:32:08'),
(15, '197210023615', 'DagBergqvist@jourrapide.com', '04854157160', '08a962c1b9f8964a03779209178939a368a07275670f596e23e4f92f83de40e96f4837c325e70d520f0003a242f864f8', 'Alsteråvägen 59', 'Dag Bergqvist', '2023-05-30 10:33:34'),
(16, '198002025412', 'AlvarOlofsson@jourrapide.com', '04568340727', '2770b2ae965587cf771a8b37d411070d95801add288db4a718dc11a4670fd7477e39ad12d4c2c9d798ef1e58d6468550', 'Törneby 74', 'Alvar Olofsson', '2023-05-30 10:37:06'),
(17, '194412139240', 'WeraNyman@teleworm.us', '01752699226', 'a096cb686571fec2b661ea34a6ad4533415ab306bf833b5393adb633442cffea575e0ff4bfec2efcb54d987c0366fab9', 'Simpnäs 94', 'Wera Nyman', '2023-05-30 10:38:30'),
(18, '199404255763', 'SaraEliasson@dayrep.com', '0898674549', '285f60d0ae3761e50e71275d12aab5905fbe3ccb9a9412b011d9b1612aef56eae69d22971b855b923fffbdab086074b7', 'Stallstigen 69', 'Sara Eliasson', '2023-05-30 10:41:47'),
(19, '196007104745', 'MajaLindgren@dayrep.com', '04989058602', '9d42f7ece7c49c3dccd9306ddc090dca38c9b6d3ec51c9b44b282bfe93ccc22b37f6db5645549ac830b4fcd30afbd80b', 'Änggårda Anga 38', 'Maja Lindgren', '2023-05-30 10:44:01'),
(20, '197906169276', 'EnzoMartensson@dayrep.com', '04983914045', 'c436429c12d49183c036748e203bb0de92f95d81800933072e695e6b4844159416613467c7d4298a81da7fb0d2056094', 'Skolspåret 20', 'Enzo Mårtensson', '2023-05-30 10:45:18'),
(21, '198305145172', 'YonisJohansson@dayrep.com', '05825384520', '1a74cb4ed33539e09462b8d517cbf370cf76760e57d5f03fc25903003259339f9bb4e44fc025e9f6bc8c98473cf93759', 'Libecksvägen 52', 'Yonis Johansson', '2023-06-01 13:44:02'),
(22, '200301084596', 'JohanHolm@dayrep.com', '0706079359', '87877525389acb30bb308611fe0b05903aeb31a477f276f0b130fa5849716cbfa9339ce467ac665291f53b3bf0933dea', 'Hantverkarg 82', 'Johan Holm', '2023-06-01 20:57:04'),
(23, '199104283529', 'MillyLofgren@jourrapide.com', '05152473566', 'f4a664af6774eb3a47edbf38211318e6732396cdfbc486b85d35f1032c851469bdb7fb4bd76056532e029e3cf8c42a38', 'Löberöd 40', 'Milly Löfgren', '2023-06-02 13:33:28'),
(24, '200105073969', 'LavaGunnarsson@armyspy.com', '0409686734', 'b35e6ac4bf1eb0dd22fd50e4f6cfd3f7fce7cb35386b79588a2a55e422e16031eca8d20ef1b9defdd1e47dee261483ba', 'Handslagarevägen 68', 'Lava Gunnarsson', '2023-06-02 13:38:59');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
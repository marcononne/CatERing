-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Gen 16, 2024 alle 14:53
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `catering`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `Availabilities`
--

CREATE TABLE `Availabilities` (
  `id` int(11) NOT NULL,
  `startingDate` date NOT NULL,
  `endingDate` date NOT NULL,
  `startingTime` time NOT NULL,
  `endingTime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ComplexEvents`
--

CREATE TABLE `ComplexEvents` (
  `id` int(11) NOT NULL,
  `endingDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Events`
--

CREATE TABLE `Events` (
  `event_id` int(11) NOT NULL,
  `customer` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `startingDate` date NOT NULL,
  `location` varchar(128) NOT NULL,
  `status` varchar(128) NOT NULL,
  `startingTime` time NOT NULL,
  `endingTime` time NOT NULL,
  `participants` int(11) NOT NULL,
  `services` int(11) DEFAULT 1,
  `notes` varchar(1024) DEFAULT NULL,
  `addDocs` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ItemsRecipes`
--

CREATE TABLE `ItemsRecipes` (
  `itemId` int(11) NOT NULL,
  `recipeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `KitchenShifts`
--

CREATE TABLE `KitchenShifts` (
  `taskId` int(11) NOT NULL,
  `shiftId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `MembersAvailabilties`
--

CREATE TABLE `MembersAvailabilties` (
  `memberId` int(11) NOT NULL,
  `availabilityId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `MembersShifts`
--

CREATE TABLE `MembersShifts` (
  `memberId` int(11) NOT NULL,
  `shiftId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `MembersSummons`
--

CREATE TABLE `MembersSummons` (
  `memberId` int(11) NOT NULL,
  `summonId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `MembersTasks`
--

CREATE TABLE `MembersTasks` (
  `memberId` int(11) NOT NULL,
  `taskId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Menu`
--

CREATE TABLE `Menu` (
  `id` int(11) NOT NULL,
  `title` int(11) NOT NULL,
  `published` tinyint(1) DEFAULT 0,
  `inUse` tinyint(1) DEFAULT 0,
  `cookRequired` tinyint(1) NOT NULL,
  `onSiteMeals` tinyint(1) NOT NULL,
  `kitchenRequired` tinyint(1) NOT NULL,
  `buffet` tinyint(1) NOT NULL,
  `fingerFood` tinyint(1) NOT NULL,
  `serviceID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `MenuItems`
--

CREATE TABLE `MenuItems` (
  `id` int(11) NOT NULL,
  `description` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `PeriodicEventInstances`
--

CREATE TABLE `PeriodicEventInstances` (
  `instanceID` int(11) NOT NULL,
  `mainEventId` int(11) NOT NULL,
  `executionDate` date NOT NULL,
  `location` varchar(128) DEFAULT NULL,
  `startingTime` time DEFAULT NULL,
  `endingTime` time DEFAULT NULL,
  `participants` int(11) DEFAULT NULL,
  `services` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `PeriodicEvents`
--

CREATE TABLE `PeriodicEvents` (
  `id` int(11) NOT NULL,
  `endingDate` date NOT NULL,
  `frequency` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Procedures`
--

CREATE TABLE `Procedures` (
  `id` int(11) NOT NULL,
  `owner` int(11) NOT NULL,
  `author` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `resultingQty` int(11) NOT NULL,
  `ingredientsQty` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`ingredientsQty`)),
  `ingredients` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`ingredients`)),
  `tag` varchar(128) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  `instructions` varchar(1024) DEFAULT NULL,
  `published` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Sections`
--

CREATE TABLE `Sections` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `menuId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Services`
--

CREATE TABLE `Services` (
  `id` int(11) NOT NULL,
  `eventId` int(11) NOT NULL,
  `type` varchar(128) NOT NULL,
  `status` varchar(128) DEFAULT NULL,
  `participants` int(11) NOT NULL,
  `location` varchar(128) NOT NULL,
  `date` date NOT NULL,
  `startingTime` time NOT NULL,
  `endingTime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Shifts`
--

CREATE TABLE `Shifts` (
  `id` int(11) NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `date` date NOT NULL,
  `workplace` varchar(128) DEFAULT NULL,
  `full` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `StaffMembers`
--

CREATE TABLE `StaffMembers` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `job` varchar(128) NOT NULL COMMENT '"Cook", "Waiter"',
  `role` varchar(128) DEFAULT NULL COMMENT '"Pours wine","Restocks bread"'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `SummarySheets`
--

CREATE TABLE `SummarySheets` (
  `id` int(11) NOT NULL,
  `service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `SummarySheetTasks`
--

CREATE TABLE `SummarySheetTasks` (
  `sumSheetId` int(11) NOT NULL,
  `taskId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Summons`
--

CREATE TABLE `Summons` (
  `id` int(11) NOT NULL,
  `startingDate` date NOT NULL,
  `endingDate` date NOT NULL,
  `startingTime` time NOT NULL,
  `endingTime` date NOT NULL,
  `status` varchar(128) NOT NULL COMMENT '"active","assigned","revoked"'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Tasks`
--

CREATE TABLE `Tasks` (
  `id` int(11) NOT NULL,
  `procedure` int(11) NOT NULL,
  `priority` int(11) NOT NULL,
  `estimatedTime` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `completed` tinyint(1) DEFAULT 0,
  `toDo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `UpdateSuggestions`
--

CREATE TABLE `UpdateSuggestions` (
  `id` int(11) NOT NULL,
  `type` varchar(128) NOT NULL,
  `approved` tinyint(1) NOT NULL,
  `serviceId` int(11) NOT NULL,
  `menuItemId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `role` varchar(20) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Struttura della tabella `UsersEvents`
--

CREATE TABLE `UsersEvents` (
  `userId` int(11) NOT NULL,
  `eventId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `UsersSummons`
--

CREATE TABLE `UsersSummons` (
  `userId` int(11) NOT NULL,
  `summonId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `Availabilities`
--
ALTER TABLE `Availabilities`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `ComplexEvents`
--
ALTER TABLE `ComplexEvents`
  ADD KEY `ComplexEvents_Events_events_id_fk` (`id`);

--
-- Indici per le tabelle `Events`
--
ALTER TABLE `Events`
  ADD PRIMARY KEY (`event_id`);

--
-- Indici per le tabelle `ItemsRecipes`
--
ALTER TABLE `ItemsRecipes`
  ADD KEY `ItemsRecipes_MenuItems_id_fk` (`itemId`),
  ADD KEY `ItemsRecipes_Procedures_id_fk` (`recipeId`);

--
-- Indici per le tabelle `KitchenShifts`
--
ALTER TABLE `KitchenShifts`
  ADD KEY `KitchenShifts_Shifts_id_fk` (`shiftId`),
  ADD KEY `KitchenShifts_Tasks_id_fk` (`taskId`);

--
-- Indici per le tabelle `MembersAvailabilties`
--
ALTER TABLE `MembersAvailabilties`
  ADD KEY `MembersAvailabilties_Availabilities_id_fk` (`availabilityId`),
  ADD KEY `MembersAvailabilties_StaffMembers_id_fk` (`memberId`);

--
-- Indici per le tabelle `MembersShifts`
--
ALTER TABLE `MembersShifts`
  ADD KEY `MembersShifts_Shifts_id_fk` (`shiftId`),
  ADD KEY `MembersShifts_StaffMembers_id_fk` (`memberId`);

--
-- Indici per le tabelle `MembersSummons`
--
ALTER TABLE `MembersSummons`
  ADD KEY `MembersSummons_StaffMembers_id_fk` (`memberId`),
  ADD KEY `MembersSummons_Summons_id_fk` (`summonId`);

--
-- Indici per le tabelle `MembersTasks`
--
ALTER TABLE `MembersTasks`
  ADD KEY `MembersTasks_StaffMembers_id_fk` (`memberId`),
  ADD KEY `MembersTasks_Tasks_id_fk` (`taskId`);

--
-- Indici per le tabelle `Menu`
--
ALTER TABLE `Menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Menu_Services_id_fk` (`serviceID`);

--
-- Indici per le tabelle `MenuItems`
--
ALTER TABLE `MenuItems`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `PeriodicEventInstances`
--
ALTER TABLE `PeriodicEventInstances`
  ADD PRIMARY KEY (`instanceID`),
  ADD KEY `PeriodicEventInstances_PeriodicEvents_id_fk` (`mainEventId`);

--
-- Indici per le tabelle `PeriodicEvents`
--
ALTER TABLE `PeriodicEvents`
  ADD KEY `PeriodicEvents_Events_events_id_fk` (`id`);

--
-- Indici per le tabelle `Procedures`
--
ALTER TABLE `Procedures`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Procedures_StaffMembers_id_fk` (`owner`),
  ADD KEY `Procedures_Users_id_fk` (`author`);

--
-- Indici per le tabelle `Sections`
--
ALTER TABLE `Sections`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Sections_Menu_id_fk` (`menuId`);

--
-- Indici per le tabelle `Services`
--
ALTER TABLE `Services`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Services_Events_events_id_fk` (`eventId`);

--
-- Indici per le tabelle `Shifts`
--
ALTER TABLE `Shifts`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `StaffMembers`
--
ALTER TABLE `StaffMembers`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `SummarySheets`
--
ALTER TABLE `SummarySheets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `SummarySheets_Services_id_fk` (`service`);

--
-- Indici per le tabelle `SummarySheetTasks`
--
ALTER TABLE `SummarySheetTasks`
  ADD KEY `SummarySheetTasks_SummarySheets_id_fk` (`sumSheetId`),
  ADD KEY `SummarySheetTasks_Tasks_id_fk` (`taskId`);

--
-- Indici per le tabelle `Summons`
--
ALTER TABLE `Summons`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `Tasks`
--
ALTER TABLE `Tasks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Tasks_Procedures_id_fk` (`procedure`);

--
-- Indici per le tabelle `UpdateSuggestions`
--
ALTER TABLE `UpdateSuggestions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `UpdateSuggestions_Services_id_fk` (`serviceId`),
  ADD KEY `UpdateSuggestions_MenuItems_id_fk` (`menuItemId`);

--
-- Indici per le tabelle `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `UsersEvents`
--
ALTER TABLE `UsersEvents`
  ADD KEY `UsersEvents_Events_id_fk` (`eventId`),
  ADD KEY `UsersEvents_Users_id_fk` (`userId`);

--
-- Indici per le tabelle `UsersSummons`
--
ALTER TABLE `UsersSummons`
  ADD KEY `UsersSummons_Summons_id_fk` (`summonId`),
  ADD KEY `UsersSummons_Users_id_fk` (`userId`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `Availabilities`
--
ALTER TABLE `Availabilities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Events`
--
ALTER TABLE `Events`
  MODIFY `event_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Menu`
--
ALTER TABLE `Menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `MenuItems`
--
ALTER TABLE `MenuItems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `PeriodicEventInstances`
--
ALTER TABLE `PeriodicEventInstances`
  MODIFY `instanceID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Procedures`
--
ALTER TABLE `Procedures`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Sections`
--
ALTER TABLE `Sections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Services`
--
ALTER TABLE `Services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Shifts`
--
ALTER TABLE `Shifts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `StaffMembers`
--
ALTER TABLE `StaffMembers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `SummarySheets`
--
ALTER TABLE `SummarySheets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Summons`
--
ALTER TABLE `Summons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Tasks`
--
ALTER TABLE `Tasks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `UpdateSuggestions`
--
ALTER TABLE `UpdateSuggestions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `ComplexEvents`
--
ALTER TABLE `ComplexEvents`
  ADD CONSTRAINT `ComplexEvents_Events_events_id_fk` FOREIGN KEY (`id`) REFERENCES `Events` (`event_id`);

--
-- Limiti per la tabella `ItemsRecipes`
--
ALTER TABLE `ItemsRecipes`
  ADD CONSTRAINT `ItemsRecipes_MenuItems_id_fk` FOREIGN KEY (`itemId`) REFERENCES `MenuItems` (`id`),
  ADD CONSTRAINT `ItemsRecipes_Procedures_id_fk` FOREIGN KEY (`recipeId`) REFERENCES `Procedures` (`id`);

--
-- Limiti per la tabella `KitchenShifts`
--
ALTER TABLE `KitchenShifts`
  ADD CONSTRAINT `KitchenShifts_Shifts_id_fk` FOREIGN KEY (`shiftId`) REFERENCES `Shifts` (`id`),
  ADD CONSTRAINT `KitchenShifts_Tasks_id_fk` FOREIGN KEY (`taskId`) REFERENCES `Tasks` (`id`);

--
-- Limiti per la tabella `MembersAvailabilties`
--
ALTER TABLE `MembersAvailabilties`
  ADD CONSTRAINT `MembersAvailabilties_Availabilities_id_fk` FOREIGN KEY (`availabilityId`) REFERENCES `Availabilities` (`id`),
  ADD CONSTRAINT `MembersAvailabilties_StaffMembers_id_fk` FOREIGN KEY (`memberId`) REFERENCES `StaffMembers` (`id`);

--
-- Limiti per la tabella `MembersShifts`
--
ALTER TABLE `MembersShifts`
  ADD CONSTRAINT `MembersShifts_Shifts_id_fk` FOREIGN KEY (`shiftId`) REFERENCES `Shifts` (`id`),
  ADD CONSTRAINT `MembersShifts_StaffMembers_id_fk` FOREIGN KEY (`memberId`) REFERENCES `StaffMembers` (`id`);

--
-- Limiti per la tabella `MembersSummons`
--
ALTER TABLE `MembersSummons`
  ADD CONSTRAINT `MembersSummons_StaffMembers_id_fk` FOREIGN KEY (`memberId`) REFERENCES `StaffMembers` (`id`),
  ADD CONSTRAINT `MembersSummons_Summons_id_fk` FOREIGN KEY (`summonId`) REFERENCES `Summons` (`id`);

--
-- Limiti per la tabella `MembersTasks`
--
ALTER TABLE `MembersTasks`
  ADD CONSTRAINT `MembersTasks_StaffMembers_id_fk` FOREIGN KEY (`memberId`) REFERENCES `StaffMembers` (`id`),
  ADD CONSTRAINT `MembersTasks_Tasks_id_fk` FOREIGN KEY (`taskId`) REFERENCES `Tasks` (`id`);

--
-- Limiti per la tabella `Menu`
--
ALTER TABLE `Menu`
  ADD CONSTRAINT `Menu_Services_id_fk` FOREIGN KEY (`serviceID`) REFERENCES `Services` (`id`);

--
-- Limiti per la tabella `PeriodicEventInstances`
--
ALTER TABLE `PeriodicEventInstances`
  ADD CONSTRAINT `PeriodicEventInstances_PeriodicEvents_id_fk` FOREIGN KEY (`mainEventId`) REFERENCES `PeriodicEvents` (`id`);

--
-- Limiti per la tabella `PeriodicEvents`
--
ALTER TABLE `PeriodicEvents`
  ADD CONSTRAINT `PeriodicEvents_Events_events_id_fk` FOREIGN KEY (`id`) REFERENCES `Events` (`event_id`);

--
-- Limiti per la tabella `Procedures`
--
ALTER TABLE `Procedures`
  ADD CONSTRAINT `Procedures_StaffMembers_id_fk` FOREIGN KEY (`owner`) REFERENCES `StaffMembers` (`id`),
  ADD CONSTRAINT `Procedures_Users_id_fk` FOREIGN KEY (`author`) REFERENCES `Users` (`id`);

--
-- Limiti per la tabella `Sections`
--
ALTER TABLE `Sections`
  ADD CONSTRAINT `Sections_Menu_id_fk` FOREIGN KEY (`menuId`) REFERENCES `Menu` (`id`);

--
-- Limiti per la tabella `Services`
--
ALTER TABLE `Services`
  ADD CONSTRAINT `Services_Events_events_id_fk` FOREIGN KEY (`eventId`) REFERENCES `Events` (`event_id`);

--
-- Limiti per la tabella `SummarySheets`
--
ALTER TABLE `SummarySheets`
  ADD CONSTRAINT `SummarySheets_Services_id_fk` FOREIGN KEY (`service`) REFERENCES `Services` (`id`);

--
-- Limiti per la tabella `SummarySheetTasks`
--
ALTER TABLE `SummarySheetTasks`
  ADD CONSTRAINT `SummarySheetTasks_SummarySheets_id_fk` FOREIGN KEY (`sumSheetId`) REFERENCES `SummarySheets` (`id`),
  ADD CONSTRAINT `SummarySheetTasks_Tasks_id_fk` FOREIGN KEY (`taskId`) REFERENCES `Tasks` (`id`);

--
-- Limiti per la tabella `Tasks`
--
ALTER TABLE `Tasks`
  ADD CONSTRAINT `Tasks_Procedures_id_fk` FOREIGN KEY (`procedure`) REFERENCES `Procedures` (`id`);

--
-- Limiti per la tabella `UpdateSuggestions`
--
ALTER TABLE `UpdateSuggestions`
  ADD CONSTRAINT `UpdateSuggestions_MenuItems_id_fk` FOREIGN KEY (`menuItemId`) REFERENCES `MenuItems` (`id`),
  ADD CONSTRAINT `UpdateSuggestions_Services_id_fk` FOREIGN KEY (`serviceId`) REFERENCES `Services` (`id`);

--
-- Limiti per la tabella `UsersEvents`
--
ALTER TABLE `UsersEvents`
  ADD CONSTRAINT `UsersEvents_Events_id_fk` FOREIGN KEY (`eventId`) REFERENCES `Events` (`event_id`),
  ADD CONSTRAINT `UsersEvents_Users_id_fk` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`);

--
-- Limiti per la tabella `UsersSummons`
--
ALTER TABLE `UsersSummons`
  ADD CONSTRAINT `UsersSummons_Summons_id_fk` FOREIGN KEY (`summonId`) REFERENCES `Summons` (`id`),
  ADD CONSTRAINT `UsersSummons_Users_id_fk` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
